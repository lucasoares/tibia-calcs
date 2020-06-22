/*
 * Tibia Calcs GPL Source Code
 * Copyright (C) 2020 Lucas Soares de Miranda
 * Copyright (C) 2020 Luiz Claudio Soares de Miranda
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
 */
package com.tibiacalcs.processor.data.player.online;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.bulk.BulkWriteResult;
import com.tibiacalcs.core.events.Event;
import com.tibiacalcs.core.events.EventRepository;
import com.tibiacalcs.core.events.impl.level.UpLevelEvent;
import com.tibiacalcs.core.events.impl.login.LogInEvent;
import com.tibiacalcs.core.events.impl.login.LogOutEvent;
import com.tibiacalcs.core.player.Player;
import com.tibiacalcs.core.player.PlayerRepository;
import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.processor.task.Task;
import com.tibiacalcs.processor.task.TaskType;
import com.tibiacalcs.tibiadata.api.world.WorldResponse;
import com.tibiacalcs.tibiadata.api.world.entities.OnlinePlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;

@Log4j2
@RequiredArgsConstructor
public class OnlinePlayersProcessor extends Task {

  // Resources
  private final WorldResponse response;
  private final PlayerRepository playerRepository;
  private final EventRepository eventRepository;
  private final MongoTemplate mongoTemplate;

  // Fields used to store all process results
  private final List<Player> newPlayers = new ArrayList<>();
  private final List<Event> events = new ArrayList<>();
  private final Map<String, Update> playerUpdates = new HashMap<>();

  // Fields used to process
  Map<String, OnlinePlayer> playersOnlineNowByName;
  Map<String, Player> databasePlayers;

  @Override
  protected TaskType getType() {
    return TaskType.ONLINE_PLAYERS_PROCESSOR;
  }

  protected void processImpl() {
    List<OnlinePlayer> onlinePlayers = response.getWorld().getPlayersOnline();

    String worldName = response.getWorld().getWorldInformation().getName();

    this.playersOnlineNowByName =
        onlinePlayers.stream().collect(Collectors.toMap(OnlinePlayer::getName, item -> item));

    this.databasePlayers =
        mapPlayersByName(
            this.playerRepository.findAllByWorldAndOnlineOrNameIn(worldName,
                true, this.playersOnlineNowByName.keySet()));

    processLoginEvents();

    updatePlayers();

    store();
  }

  void store() {
    if (!this.events.isEmpty()) {
      log.debug(
          "{} new events found. Events: {}",
          this.events.size(),
          this.events.stream()
              .map(Object::getClass)
              .map(Class::getSimpleName)
              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

      this.eventRepository.insert(this.events);
    }

    if (!this.newPlayers.isEmpty()) {
      log.debug("{} new players found.", this.newPlayers.size());

      this.playerRepository.insert(this.newPlayers);
    }

    if (!this.playerUpdates.isEmpty()) {
      BulkOperations playerOperations =
          this.mongoTemplate.bulkOps(BulkMode.UNORDERED, Player.class);

      this.playerUpdates.forEach(
          (name, update) -> playerOperations.updateOne(query(where("name").is(name)), update));

      Timer timer = new Timer();
      BulkWriteResult result = playerOperations.execute();

      log.debug(
          "Bulk sent in {}ms. Matched: {}, inserted: {}, modified: {}, deleted: {}, upserts: {}.",
          timer.elapsedMillisTime(),
          result.getMatchedCount(),
          result.getInsertedCount(),
          result.getModifiedCount(),
          result.getDeletedCount(),
          result.getUpserts().size());
    }
  }

  void updatePlayers() {
    for (Entry<String, OnlinePlayer> entry : this.playersOnlineNowByName.entrySet()) {
      String name = entry.getKey();
      OnlinePlayer newPlayer = entry.getValue();

      if (!this.databasePlayers.containsKey(name)) {
        this.newPlayers.add(Player.fromOnline(response.getWorld().getWorldInformation().getName(),
            response.getInformation().getLastUpdated(), newPlayer));

        continue;
      }

      Player oldPlayer = this.databasePlayers.get(name);

      int oldLvl = oldPlayer.getLvl();
      int newLvl = newPlayer.getLevel();

      // Dealing with vocation promotion
      String oldVoc = oldPlayer.getVoc();
      String newVoc = newPlayer.getVocation();

      if (oldLvl == newLvl && oldVoc.equals(newVoc)) {
        continue;
      }

      if (newLvl > oldLvl) {
        this.events.add(
            new UpLevelEvent(response.getInformation().getLastUpdated(), newPlayer.getName(),
                oldLvl, newLvl));
      }

      Update playerUpdate = getPlayerUpdates(newPlayer.getName());

      if (oldLvl != newLvl) {
        playerUpdate.set("lvl", newPlayer.getLevel());
      }

      if (!newVoc.equals(oldVoc)) {
        playerUpdate.set("voc", newVoc);
      }
    }
  }

  private Update getPlayerUpdates(String name) {
    return this.playerUpdates.computeIfAbsent(name, x -> new Update());
  }

  private Map<String, Player> mapPlayersByName(Collection<Player> player) {
    return player.stream().collect(Collectors.toMap(Player::getName, item -> item));
  }

  void processLoginEvents() {
    for (Entry<String, Player> entry : this.databasePlayers.entrySet()) {
      String name = entry.getKey();
      Player player = entry.getValue();

      if (player.isOnline() && !this.playersOnlineNowByName.containsKey(name)) {
        this.events.add(new LogOutEvent(response.getInformation().getLastUpdated(), name,
            player.getOnlineSince()));

        getPlayerUpdates(name).set("online", false).unset("onlineSince");
      }
    }

    for (String name : this.playersOnlineNowByName.keySet()) {
      Player databasePlayer = this.databasePlayers.get(name);

      if (databasePlayer != null && databasePlayer.isOnline()) {
        continue;
      }

      this.events.add(new LogInEvent(response.getInformation().getLastUpdated(), name));

      if (databasePlayer != null) {
        getPlayerUpdates(name).set("online", true)
            .set("onlineSince", response.getInformation().getLastUpdated());
      }
    }
  }
}

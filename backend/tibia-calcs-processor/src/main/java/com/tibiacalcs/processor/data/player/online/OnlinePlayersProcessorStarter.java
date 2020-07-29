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

import com.tibiacalcs.core.events.EventRepository;
import com.tibiacalcs.core.player.PlayerRepository;
import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.processor.data.world.WorldUpdater;
import com.tibiacalcs.tibiadata.api.world.WorldResponse;
import com.tibiacalcs.tibiadata.api.world.WorldsApi;
import com.tibiacalcs.tibiadata.api.world.entities.OnlinePlayer;
import com.tibiacalcs.tibiadata.api.world.entities.WorldInformation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(OnlinePlayersProperties.class)
public class OnlinePlayersProcessorStarter implements ApplicationEventPublisherAware {

  private final WorldsApi worldsApi;
  private final PlayerRepository playerRepository;
  private final EventRepository eventRepository;
  private final MongoTemplate mongoTemplate;
  private final WorldUpdater worldUpdater;
  private final OnlinePlayersProperties onlinePlayersProperties;
  private ApplicationEventPublisher publisher;

  // Cache
  private final Map<String, Set<String>> playersNameByWorld = new HashMap<>();

  @Scheduled(fixedDelay = 20 * 1000)
  public void startOnlinePlayersProcessor() {
    if (this.worldUpdater.getWorldList().isEmpty()) {
      log.debug("0 worlds to collect player online information.");

      return;
    }

    Timer timer = new Timer();

    try {
      this.worldUpdater.getWorldList().parallelStream()
          .filter(this::filterWorld)
          .map(world -> this.worldsApi.world(world.getName()))
          .filter(Objects::nonNull)
          .filter(WorldResponse::isValid)
          .filter(this::cacheMiss)
          .map(worldResponse -> new OnlinePlayersEvent(this, worldResponse))
          .forEach(this.publisher::publishEvent);
    } catch (Exception e) {
      log.error("Error running online players starter.", e);

    } finally {
      log.debug("Online players starter took {}ms to run.", timer.elapsedMillisTime());
    }
  }

  private boolean filterWorld(WorldInformation world) {
    return this.onlinePlayersProperties.getWorlds().isEmpty()
        || this.onlinePlayersProperties
        .getWorlds().contains(world.getName());
  }

  @Async
  @EventListener
  public void onWorldEvent(OnlinePlayersEvent worldOnlinePlayers) {
    WorldResponse response = worldOnlinePlayers.getWorldResponse();

    log.debug("Starting online players processor for {}.",
        response.getWorld().getWorldInformation().getName());

    new OnlinePlayersProcessor(
        response, this.playerRepository, this.eventRepository,
        this.mongoTemplate)
        .process();
  }

  private boolean cacheMiss(WorldResponse worldResponse) {
    if (worldResponse.getWorld().getPlayersOnline() == null) {
      worldResponse.getWorld().setPlayersOnline(new ArrayList<>());
    }

    String world = worldResponse.getWorld().getWorldInformation().getName();

    Set<String> names =
        worldResponse.getWorld().getPlayersOnline().stream().map(OnlinePlayer::getName)
            .collect(
                Collectors.toSet());

    if (this.playersNameByWorld.get(world) != null && this.playersNameByWorld.get(world)
        .equals(names)) {
      log.trace("Cache hit. Ignoring results.");

      return false;
    }

    this.playersNameByWorld.put(world, names);

    return true;
  }

  @Override
  public void setApplicationEventPublisher(
      @NonNull ApplicationEventPublisher applicationEventPublisher) {
    this.publisher = applicationEventPublisher;
  }
}

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
package com.tibiacalcs.processor.statistics.secondcharacter;

import com.tibiacalcs.core.events.impl.login.LogOutEvent;
import com.tibiacalcs.core.events.impl.login.LogOutEventRepository;
import com.tibiacalcs.core.relation.PlayerLoginRelation;
import com.tibiacalcs.core.relation.PlayerLoginRelationRepository;
import com.tibiacalcs.processor.task.Task;
import com.tibiacalcs.processor.task.TaskType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SecondCharacterFinder extends Task {

  // Resources
  private final Set<String> playerNames;
  private final LogOutEventRepository logOutEventRepository;
  private final PlayerLoginRelationRepository playerLoginRelationRepository;

  @Override
  protected TaskType getType() {
    return TaskType.SECOND_CHARACTER_FINDER;
  }

  protected void processImpl() throws InterruptedException {
    List<String> players = new ArrayList<>(this.playerNames);
    this.playerNames.clear();

    Collections.sort(players);

    List<LogOutEvent> logOutEvents = this.logOutEventRepository.findAll();
    Map<String, List<LogOutEvent>> logOutEventsByName =
        logOutEvents.stream()
            .collect(Collectors.groupingBy(LogOutEvent::getName));

    log.debug("{} log out events to process.", logOutEvents.size());
    logOutEvents.clear();

    Map<String, PlayerLoginRelation> relations = new HashMap<>();

    ExecutorService executor = Executors.newFixedThreadPool(48);

    for (int i = 0; i < players.size() - 1; i++) {
      int playerPosition = i;

      executor.execute(
          () -> processPlayerRelation(players, logOutEventsByName, relations, playerPosition));
    }

    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

    log.debug("Storing {} relations.", relations.size());

    this.playerLoginRelationRepository.deleteAll();

    List<PlayerLoginRelation> toSave = new ArrayList<>();
    for (PlayerLoginRelation value : relations.values()) {
      toSave.add(value);

      if (toSave.size() >= 5000) {
        this.playerLoginRelationRepository.insert(toSave);
        toSave.clear();
      }
    }
  }

  private void processPlayerRelation(List<String> players,
      Map<String, List<LogOutEvent>> logOutEventsByName, Map<String, PlayerLoginRelation> overlaps,
      int i) {
    String from = players.get(i);

    List<LogOutEvent> fromLogOuts = logOutEventsByName.get(from);

    if (fromLogOuts == null) {
      return;
    }

    for (int j = i + 1; j < players.size(); j++) {
      String to = players.get(j);

      List<LogOutEvent> toLogOuts = logOutEventsByName.get(to);

      if (toLogOuts == null) {
        continue;
      }

      for (int l = 0; l < fromLogOuts.size(); l++) {
        LogOutEvent fromLogOut = fromLogOuts.get(l);

        for (int k = 0; k < toLogOuts.size(); k++) {
          LogOutEvent toLogOut = toLogOuts.get(k);

          long fromStart = fromLogOut.getIn().getTime();
          long fromEnd = fromLogOut.getDate().getTime();
          long toStart = toLogOut.getIn().getTime();
          long toEnd = toLogOut.getDate().getTime();

          boolean overlap = fromStart < toEnd && toStart < fromEnd;

          PlayerLoginRelation overlapData = get(from, to, overlaps);

          long maxStart = Math.max(fromStart, toStart);
          long minEnd = Math.min(fromEnd, toEnd);
          long span = minEnd - maxStart;

          if (overlap) {
            overlapData.overlap(span);
          } else {
            if (span < TimeUnit.MINUTES.toMillis(6)) {
              overlapData.consecutiveLogin();
            }
          }
        }
      }
    }
  }

  public synchronized PlayerLoginRelation get(String from, String to,
      Map<String, PlayerLoginRelation> map) {
    return map
        .computeIfAbsent(from + '_' + to, x -> new PlayerLoginRelation().setFrom(from).setTo(to));
  }
}

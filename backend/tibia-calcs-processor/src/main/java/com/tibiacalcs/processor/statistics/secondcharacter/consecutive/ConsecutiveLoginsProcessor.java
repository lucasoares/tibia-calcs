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
package com.tibiacalcs.processor.statistics.secondcharacter.consecutive;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.Relation;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.RelationType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class ConsecutiveLoginsProcessor {

  public List<Relation> process(@NonNull SortedSet<String> playersWasOnline,
      @NonNull SortedSet<String> playersIsOnline) {
    Timer timer = new Timer();

    SetView<String> logins = Sets
        .difference(playersIsOnline, playersWasOnline);

    log.debug("{} players logged in.", logins.size());

    SetView<String> logouts = Sets
        .difference(playersWasOnline, playersIsOnline);

    log.debug("{} players logged out.", logouts.size());

    if (logins.isEmpty() || logouts.isEmpty()) {
      return Collections.emptyList();
    }

    List<Relation> relations = new ArrayList<>();

    for (String from : logins) {
      for (String to : logouts) {
        if (from.compareTo(to) < 0) {
          relations.add(
              new Relation(from, to, RelationType.CONSECUTIVE));

          continue;
        }

        relations.add(
            new Relation(to, from, RelationType.CONSECUTIVE));
      }
    }

    log.info("{} consecutive logins found in {}ms.", logins.size() * logouts.size(),
        timer.elapsedMillisTime());

    return relations;
  }
}

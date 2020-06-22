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

import com.mongodb.BasicDBObject;
import com.tibiacalcs.core.events.impl.login.LogOutEventRepository;
import com.tibiacalcs.core.relation.PlayerLoginRelationRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecondCharacterFinderScheduler {

  private final LogOutEventRepository logOutEventRepository;
  private final PlayerLoginRelationRepository playerLoginRelationRepository;
  private final MongoTemplate mongoTemplate;

  private static final String WORLD = "Honbra";

  /**
   * Commented since we need to find a better way to compute and store those relations.
   *
   * We have a Free MongoDB server with only 500MB ram and the relations resulted from this method
   * are bigger than 5GB for a single world.
   */
  //@Scheduled(initialDelay = 0, fixedDelay = 10 * 60 * 1000)
  @SuppressWarnings("unused")
  public void run() {
    Set<String> names = distinctNamesByWorld(WORLD, this.mongoTemplate);

    log.debug("{} players to compute relations.", names.size());

    new SecondCharacterFinder(names, this.logOutEventRepository, this.playerLoginRelationRepository)
        .process();
  }

  private Set<String> distinctNamesByWorld(String world, MongoTemplate mongoTemplate) {
    return mongoTemplate
        .getCollection("players")
        .distinct("name", new BasicDBObject("world", world), String.class)
        .into(new HashSet<>());
  }
}

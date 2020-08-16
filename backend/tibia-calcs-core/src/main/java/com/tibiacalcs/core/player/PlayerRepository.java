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
package com.tibiacalcs.core.player;

import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PlayerRepository extends MongoRepository<Player, String> {

  @Query(fields = "{name : 1, lvl : 1, online : 1, voc : 1, onlineSince : 1}")
  List<Player> findAllByWorldAndOnlineOrNameIn(String world, boolean online,
      Collection<String> name);

  List<Player> findAllByNameIn(Collection<String> name);

  @Query(value = "{'name': {$regex : '^?0$', $options: 'i'}}")
  Player findByNameRegex(String name);

  @Query(value = "{'name': {$regex : ?0, $options: 'i'}}", sort = "{'lvl' : -1}")
  List<Player> findAllByNameRegexOrderByLvlDesc(String name);
}

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

import com.tibiacalcs.processor.data.player.online.OnlinePlayersEvent;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.PlayerData;
import com.tibiacalcs.tibiadata.api.world.WorldResponse;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginRelationsProcessorStarter {

  private final ConcurrentHashMap<String, Set<PlayerData>> lastOnlinePlayers = new ConcurrentHashMap<>();

  private final MongoTemplate mongoTemplate;

  @Async
  @EventListener
  public void worldOnlinePlayers(OnlinePlayersEvent event) {
    String worldName = event.getWorldResponse().getWorld().getWorldInformation().getName();

    Set<PlayerData> old = this.lastOnlinePlayers.get(worldName);

    Set<PlayerData> current = convert(event.getWorldResponse());

    new LoginRelationsProcessor(old, current, this.mongoTemplate)
        .process();

    this.lastOnlinePlayers.put(worldName, current);
  }

  private Set<PlayerData> convert(
      WorldResponse worldResponse) {

    return worldResponse.getWorld().getPlayersOnline().stream()
        .map(player -> new PlayerData(player.getName())
        ).collect(Collectors.toSet());
  }
}

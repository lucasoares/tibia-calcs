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

import com.tibiacalcs.tibiadata.api.world.entities.OnlinePlayer;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "players")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

  @EqualsAndHashCode.Include
  private String name;
  private int lvl;
  private String voc;
  private Date onlineSince;
  private boolean online;
  private String guildName;
  private String world;

  // When this player was fully updated
  private Date updatedAt;

  public static Player fromOnline(String world, Date onlineSince, OnlinePlayer onlinePlayer) {
    return new Player()
        .setOnline(true)
        .setWorld(world)
        .setName(onlinePlayer.getName())
        .setLvl(onlinePlayer.getLevel())
        .setOnlineSince(onlineSince)
        .setVoc(onlinePlayer.getVocation());
  }
}

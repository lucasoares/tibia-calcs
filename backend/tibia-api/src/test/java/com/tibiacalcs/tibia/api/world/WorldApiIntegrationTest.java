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
package com.tibiacalcs.tibia.api.world;

import com.tibiacalcs.tibia.api.clients.TibiaApi;
import com.tibiacalcs.tibia.api.clients.WorldApi;
import com.tibiacalcs.tibia.api.world.entities.OnlinePlayer;
import com.tibiacalcs.tibia.api.world.exceptions.WorldNotFoundException;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Log4j2
public class WorldApiIntegrationTest {

  private static final OkHttpClient client = new OkHttpClient();

  @Test
  @Disabled
  public void onlinePlayersShouldReturnListWithoutErrors() throws IOException {
    WorldApi worldApi = new TibiaApi(client).getWorldApi();

    List<OnlinePlayer> players = worldApi.getOnlinePlayers("Honbra");

    Assertions.assertNotNull(players);
  }

  @Test
  @Disabled
  public void invalidWorldOnlinePlayersThrowsWorldNotFoundException() {
    WorldApi worldApi = new TibiaApi(client).getWorldApi();

    Assertions.assertThrows(
        WorldNotFoundException.class, () -> worldApi.getOnlinePlayers("Random"));
  }
}

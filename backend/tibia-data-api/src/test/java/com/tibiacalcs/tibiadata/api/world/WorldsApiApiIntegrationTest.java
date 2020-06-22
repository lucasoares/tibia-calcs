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
package com.tibiacalcs.tibiadata.api.world;

import com.tibiacalcs.tibiadata.api.builder.TibiaDataApiBuilder;
import com.tibiacalcs.tibiadata.test.InformationTestUtils;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Log4j2
public class WorldsApiApiIntegrationTest {

  private final OkHttpClient client = new OkHttpClient();
  private final WorldsApi worldsApi = TibiaDataApiBuilder.build(client).getWorldsApi();

  @Test
  public void singleWorldShouldReturnValidResponse() {
    WorldResponse response = worldsApi.world("Honbra");

    InformationTestUtils.validateResponseInformation(response.getInformation());

    Assertions.assertNotNull(response.getWorld());
    Assertions.assertNotNull(response.getWorld().getWorldInformation().getName());
    Assertions.assertNotNull(response.getWorld().getWorldInformation().getLocation());
  }

  @Test
  public void allWorldsShouldReturnAtLeastTenWorlds() {
    WorldListResponse response = worldsApi.worlds();

    InformationTestUtils.validateResponseInformation(response.getInformation());

    Assertions.assertTrue(response.getWorlds().getAllworlds().size() > 10);
  }

}

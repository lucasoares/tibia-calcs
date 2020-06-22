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
package com.tibiacalcs.tibia.api.clients;

import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.tibia.api.configuration.TibiaApiConfiguration;
import com.tibiacalcs.tibia.api.constants.K;
import com.tibiacalcs.tibia.api.parameters.RequestParameters;
import com.tibiacalcs.tibia.api.world.entities.OnlinePlayer;
import com.tibiacalcs.tibia.api.world.exceptions.WorldNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Log4j2
public final class WorldApi extends TibiaApiRequests {

  protected WorldApi(TibiaApiConfiguration configuration) {
    super(configuration);
  }

  public List<OnlinePlayer> getOnlinePlayers(String worldName) throws IOException {
    Document document =
        postForm(
            new RequestParameters("community")
                .queryParameter(K.SUBTOPIC, "worlds")
                .body("world", worldName));

    Date collectTime = new Date();

    Timer timer = new Timer();

    Element bodyDiv = document.selectFirst("#worlds > div.Border_2 > div > div");

    Element tableData =
        bodyDiv.selectFirst("div > table > tbody > tr > td > div > table > tbody > tr > td");

    if (tableData != null && "World with this name doesn't exist!".equals(tableData.text())) {
      throw new WorldNotFoundException(worldName);
    }

    Elements elements =
        bodyDiv.select("div:nth-child(5) > table > tbody > tr > td > div > table > tbody > tr");

    List<OnlinePlayer> players = new ArrayList<>();
    for (int i = 1; i < elements.size(); i++) {
      Element element = elements.get(i);

      String name = element.select("td > a").text();
      int lvl = Integer.parseInt(element.select("td:nth-child(2)").text());
      String voc = element.select("td:nth-child(3)").text();

      OnlinePlayer player =
          new OnlinePlayer()
              .setName(name)
              .setLvl(lvl)
              .setVoc(voc)
              .setDate(collectTime)
              .setWorld(worldName);

      players.add(player);
    }

    log.debug("{} players parsed in {}ms.", players.size(), timer.elapsedMillisTime());

    return players;
  }
}

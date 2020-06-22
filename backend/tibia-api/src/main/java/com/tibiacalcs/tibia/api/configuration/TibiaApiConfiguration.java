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
package com.tibiacalcs.tibia.api.configuration;

import com.tibiacalcs.core.timer.Timer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Log4j2
@Accessors(chain = true)
public class TibiaApiConfiguration {

  @Getter
  private final OkHttpClient client;
  @Setter
  @Getter
  private String userAgent;

  private TibiaApiConfiguration(OkHttpClient client) {
    this.client
        = client
        .newBuilder()
        .addInterceptor(
            data -> {
              Timer interceptorTimer = new Timer();
              log.debug(
                  "Requesting {} with body {} and headers {}",
                  data.request().url(),
                  data.request().body(),
                  data.request().headers().toMultimap());

              Response response = data.proceed(data.request());

              log.debug(
                  "Request code {} took {}ms.", response.code(),
                  interceptorTimer.elapsedMillisTime());

              return response;
            })
        .build();
  }

  public static TibiaApiConfiguration of(OkHttpClient client) {
    return new TibiaApiConfiguration(client);
  }
}

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
package com.tibiacalcs.tibia.api.parameters;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;

@Getter
@RequiredArgsConstructor
public final class RequestParameters {
  private static final String BASE_URL = "https://www.tibia.com";

  private final String path;

  private final Map<String, String> body = new HashMap<>();
  private final Map<String, String> queryParameters = new LinkedHashMap<>();

  public RequestParameters queryParameter(String key, String value) {
    this.queryParameters.put(key, value);

    return this;
  }

  public RequestParameters body(String key, String value) {
    this.body.put(key, value);

    return this;
  }

  public HttpUrl url() {
    boolean startsWith = this.path.startsWith("/");
    boolean endsWith = this.path.endsWith("/");

    HttpUrl url =
        HttpUrl.parse(BASE_URL + (startsWith ? "" : '/') + this.path + (endsWith ? "" : '/'));

    if (url == null) {
      throw new IllegalArgumentException("Error parsing url from path " + this.path);
    }

    if (this.queryParameters.isEmpty()) {
      return url;
    }

    HttpUrl.Builder builder = url.newBuilder();

    this.queryParameters.forEach(builder::addQueryParameter);

    return builder.build();
  }
}

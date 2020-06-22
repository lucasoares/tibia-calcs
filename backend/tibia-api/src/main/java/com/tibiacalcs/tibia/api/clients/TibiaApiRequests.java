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

import com.tibiacalcs.tibia.api.configuration.TibiaApiConfiguration;
import com.tibiacalcs.tibia.api.exception.ExecutionException;
import com.tibiacalcs.tibia.api.headers.DefaultHeaders;
import com.tibiacalcs.tibia.api.parameters.RequestParameters;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Log4j2
@RequiredArgsConstructor
abstract class TibiaApiRequests {

  private final TibiaApiConfiguration configuration;

  protected Document postForm(RequestParameters requestParameters) throws IOException {
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

    requestParameters.getBody().forEach(builder::addFormDataPart);

    HttpUrl url = requestParameters.url();

    Request request = createRequestBuilder(url).post(builder.build()).build();

    return parseResponse(executeRequest(request), url.toString());
  }

  protected Document get(RequestParameters requestParameters) throws IOException {
    HttpUrl url = requestParameters.url();
    Request request = createRequestBuilder(url).get().build();

    return parseResponse(executeRequest(request), url.toString());
  }

  private Builder createRequestBuilder(HttpUrl url) {
    Builder builder = new Builder().url(url);

    if (this.configuration.getUserAgent() != null) {
      builder.header("user-agent", this.configuration.getUserAgent());
    }

    DefaultHeaders.MAP.forEach(builder::header);

    return builder;
  }

  protected Document parseResponse(ResponseBody body, String url) throws IOException {
    return Jsoup
        .parse(body.string(), url);
  }

  private ResponseBody executeRequest(Request request) throws ExecutionException {
    Response response;
    try {
      response = this.configuration.getClient().newCall(request).execute();
    } catch (IOException e) {
      throw new ExecutionException("Error on tibia api request.", e);
    }

    if (!response.isSuccessful() || response.body() == null) {
      throw new ExecutionException("Unsuccessful request of tibia api (" + response.code() + ")",
          response);
    }

    return response.body();
  }
}

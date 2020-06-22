package com.tibiacalcs.tibiadata.api.builder;

import com.tibiacalcs.tibiadata.api.world.WorldsApi;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Log4j2
@UtilityClass
public class TibiaDataApiBuilder {

  private final String BASE_URL = "https://api.tibiadata.com/v2/";

  public TibiaDataApi build(OkHttpClient client) {
    OkHttpClient tibiaDataClient = client.newBuilder()
        .addInterceptor(
            data -> {
              long init = System.currentTimeMillis();
              log.trace(
                  "Requesting {} with body {} and headers {}",
                  data.request().url(),
                  data.request().body(),
                  data.request().headers().toMultimap());

              Response response = data.proceed(data.request());

              log.trace(
                  "Request {} response code {} took {}ms.", data.request().url(), response.code(),
                  System.currentTimeMillis() - init);

              return response;
            }).build();

    WorldsApi worldsApi = buildApi(tibiaDataClient, WorldsApi.class);

    return new TibiaDataApi(worldsApi);
  }

  private <T> T buildApi(OkHttpClient tibiaDataClient, Class<T> api) {
    return Feign.builder()
        .client(new feign.okhttp.OkHttpClient(tibiaDataClient))
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .target(api, BASE_URL);
  }
}

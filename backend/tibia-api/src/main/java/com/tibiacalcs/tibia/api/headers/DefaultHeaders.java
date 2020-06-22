package com.tibiacalcs.tibia.api.headers;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultHeaders {

  public final Map<String, String> MAP = Map
      .of("Upgrade-Insecure-Requests", "1", "Accept",
          "*/*");
}

package com.tibiacalcs.tibiadata.api.world;

import feign.Param;
import feign.RequestLine;

public interface WorldsApi {

  @RequestLine("GET /world/{worldName}.json")
  WorldResponse world(@Param("worldName") String worldName);

  @RequestLine("GET /worlds.json")
  WorldListResponse worlds();
}

package com.tibiacalcs.tibiadata.api.builder;

import com.tibiacalcs.tibiadata.api.world.WorldsApi;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TibiaDataApi {

  private final WorldsApi worldsApi;
}

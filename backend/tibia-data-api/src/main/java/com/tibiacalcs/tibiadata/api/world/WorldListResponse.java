package com.tibiacalcs.tibiadata.api.world;

import com.tibiacalcs.tibiadata.api.entities.Information;
import com.tibiacalcs.tibiadata.api.world.entities.WorldListDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorldListResponse {

  private WorldListDocument worlds;
  private Information information;
}

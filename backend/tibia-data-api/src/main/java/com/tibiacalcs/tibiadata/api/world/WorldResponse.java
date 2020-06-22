package com.tibiacalcs.tibiadata.api.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibiacalcs.tibiadata.api.entities.Information;
import com.tibiacalcs.tibiadata.api.world.entities.World;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorldResponse {

  private World world;

  private Information information;

  @JsonIgnore
  public boolean isValid() {
    return world != null && information != null;
  }
}

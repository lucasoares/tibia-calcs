package com.tibiacalcs.tibiadata.api.world.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class World {

  private WorldInformation worldInformation;

  private List<OnlinePlayer> playersOnline;
}

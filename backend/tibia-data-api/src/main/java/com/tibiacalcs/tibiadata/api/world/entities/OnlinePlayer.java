package com.tibiacalcs.tibiadata.api.world.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class OnlinePlayer {

  private String name;
  private int level;
  private String vocation;
}

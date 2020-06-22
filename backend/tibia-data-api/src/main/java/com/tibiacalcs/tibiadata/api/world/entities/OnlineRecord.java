package com.tibiacalcs.tibiadata.api.world.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tibiacalcs.tibiadata.api.deserializer.EmbededJsonDateDeserializer;
import java.util.Date;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class OnlineRecord {

  private int players;

  @JsonDeserialize(using = EmbededJsonDateDeserializer.class)
  private Date date;
}

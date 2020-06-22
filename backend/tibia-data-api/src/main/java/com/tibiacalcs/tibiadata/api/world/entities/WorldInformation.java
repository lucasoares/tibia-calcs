package com.tibiacalcs.tibiadata.api.world.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class WorldInformation {

  private String name;
  private OnlineRecord onlineRecord;
  @JsonFormat(pattern = "yyyy-MM")
  private Date creationDate;
  private String location;
  private List<String> worldQuestTitles;
  private String battleyeStatus;

  /**
   * World PvpType result in {@code /world/{name}.json}
   **/
  private String pvpType;

  /**
   * World PvpType result in {@code /worlds}
   **/
  private String worldType;

  /**
   * Players online resulted in {@code /worlds}
   **/
  private int online;

  /**
   * Players online resulted in {@code /world/{name}.json}
   **/
  private int playersOnline;

  /**
   * Additional information about the world.
   */
  private String additional;
}

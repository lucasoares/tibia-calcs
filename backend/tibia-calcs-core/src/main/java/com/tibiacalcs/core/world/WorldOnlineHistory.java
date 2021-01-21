package com.tibiacalcs.core.world;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "world_online_history")
public class WorldOnlineHistory {

  private Date date;
  private String name;
  private int online;
}

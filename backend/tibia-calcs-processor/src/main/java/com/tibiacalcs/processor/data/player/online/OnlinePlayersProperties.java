package com.tibiacalcs.processor.data.player.online;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tibiacalcs.online-players")
public class OnlinePlayersProperties {

  /**
   * World list to process online players data. If empty all worlds will be processed.
   */
  private List<String> worlds = new ArrayList<>();
}

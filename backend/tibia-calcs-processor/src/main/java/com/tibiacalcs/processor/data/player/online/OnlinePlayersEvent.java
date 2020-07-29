package com.tibiacalcs.processor.data.player.online;

import com.tibiacalcs.tibiadata.api.world.WorldResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnlinePlayersEvent extends ApplicationEvent {

  private final transient WorldResponse worldResponse;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   * associated (never {@code null})
   */
  public OnlinePlayersEvent(Object source, WorldResponse worldResponse) {
    super(source);

    this.worldResponse = worldResponse;
  }
}

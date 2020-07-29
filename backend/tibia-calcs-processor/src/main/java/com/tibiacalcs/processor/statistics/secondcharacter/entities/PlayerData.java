package com.tibiacalcs.processor.statistics.secondcharacter.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PlayerData implements Comparable<PlayerData> {

  private final String playerName;

  @Override
  public int compareTo(PlayerData o) {
    return this.playerName.compareTo(o.playerName);
  }
}

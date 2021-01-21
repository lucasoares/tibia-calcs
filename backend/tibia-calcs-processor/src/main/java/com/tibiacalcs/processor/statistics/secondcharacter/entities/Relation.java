package com.tibiacalcs.processor.statistics.secondcharacter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Relation {

  private String from;
  private String to;
  private RelationType type;
}

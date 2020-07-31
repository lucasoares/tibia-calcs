package com.tibiacalcs.processor.statistics.secondcharacter.overlap;

import com.tibiacalcs.processor.statistics.secondcharacter.entities.Relation;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.RelationType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OverlapLoginsProcessorTest {

  @Test
  public void testOverlapsCorrectSorting() {
    Set<String> players = new HashSet<>();

    players.add("char2");
    players.add("char1");
    players.add("char3");

    List<Relation> relations = OverlapLoginsProcessor
        .process(new TreeSet<>(players));

    Assertions.assertEquals(3, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.OVERLAP.equals(relation.getType())));
    Assertions.assertEquals("char1", relations.get(0).getFrom());
    Assertions.assertEquals("char2", relations.get(0).getTo());
    Assertions.assertEquals("char1", relations.get(1).getFrom());
    Assertions.assertEquals("char3", relations.get(1).getTo());
    Assertions.assertEquals("char2", relations.get(2).getFrom());
    Assertions.assertEquals("char3", relations.get(2).getTo());
  }

  @Test
  public void testOverlapsEmptyData() {
    List<Relation> relations = OverlapLoginsProcessor
        .process(new TreeSet<>());

    Assertions.assertTrue(relations.isEmpty());
  }
}

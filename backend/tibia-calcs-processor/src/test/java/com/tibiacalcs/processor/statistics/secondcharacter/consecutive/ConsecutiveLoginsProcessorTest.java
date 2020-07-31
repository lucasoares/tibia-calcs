package com.tibiacalcs.processor.statistics.secondcharacter.consecutive;

import com.tibiacalcs.processor.statistics.secondcharacter.entities.Relation;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.RelationType;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsecutiveLoginsProcessorTest {

  @Test
  public void testConsecutiveOnePlayer() {
    // Player 4 login and player 1 logout
    // Result: char1 -> char4

    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char2");
    oldPlayers.add("char1");
    oldPlayers.add("char3");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char2");
    newPlayers.add("char3");
    newPlayers.add("char4");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertEquals(1, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.CONSECUTIVE.equals(relation.getType())));
    Assertions.assertEquals("char1", relations.get(0).getFrom());
    Assertions.assertEquals("char4", relations.get(0).getTo());
  }

  @Test
  public void testConsecutiveSamePlayer() {
    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char2");
    oldPlayers.add("char1");
    oldPlayers.add("char3");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char2");
    newPlayers.add("char1");
    newPlayers.add("char3");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertTrue(relations.isEmpty());
  }

  @Test
  public void testConsecutiveMultiplePlayers() {
    // Player 4 login and player 1 logout
    // Player 5 login and player 3 logout
    // Result: char1 -> char4, char3 -> char4,  char1 -> char5, char3 -> char5

    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char2");
    oldPlayers.add("char1");
    oldPlayers.add("char3");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char5");
    newPlayers.add("char2");
    newPlayers.add("char4");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertEquals(4, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.CONSECUTIVE.equals(relation.getType())));
    Assertions.assertEquals("char1", relations.get(0).getFrom());
    Assertions.assertEquals("char4", relations.get(0).getTo());
    Assertions.assertEquals("char3", relations.get(1).getFrom());
    Assertions.assertEquals("char4", relations.get(1).getTo());
    Assertions.assertEquals("char1", relations.get(2).getFrom());
    Assertions.assertEquals("char5", relations.get(2).getTo());
    Assertions.assertEquals("char3", relations.get(3).getFrom());
    Assertions.assertEquals("char5", relations.get(3).getTo());
  }

  @Test
  public void testConsecutiveNewLoginWithLowerName() {
    // Player 0 login and player 1 logout
    // Player 5 login and player 3 logout
    // Result: char0 -> char1, char0 -> char3,  char1 -> char5, char3 -> char5

    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char1");
    oldPlayers.add("char2");
    oldPlayers.add("char3");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char2");
    newPlayers.add("char0");
    newPlayers.add("char5");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertEquals(4, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.CONSECUTIVE.equals(relation.getType())));
    Assertions.assertEquals("char0", relations.get(0).getFrom());
    Assertions.assertEquals("char1", relations.get(0).getTo());
    Assertions.assertEquals("char0", relations.get(1).getFrom());
    Assertions.assertEquals("char3", relations.get(1).getTo());
    Assertions.assertEquals("char1", relations.get(2).getFrom());
    Assertions.assertEquals("char5", relations.get(2).getTo());
    Assertions.assertEquals("char3", relations.get(3).getFrom());
    Assertions.assertEquals("char5", relations.get(3).getTo());
  }

  @Test
  public void testConsecutiveMultiplePlayersDescendingOrder() {
    // Player 4 login and player 1 logout
    // Player 5 login and player 3 logout
    // Result: char1 -> char4, char3 -> char4,  char1 -> char5, char3 -> char5

    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char3");
    oldPlayers.add("char2");
    oldPlayers.add("char1");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char5");
    newPlayers.add("char4");
    newPlayers.add("char2");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertEquals(4, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.CONSECUTIVE.equals(relation.getType())));
    Assertions.assertEquals("char1", relations.get(0).getFrom());
    Assertions.assertEquals("char4", relations.get(0).getTo());
    Assertions.assertEquals("char3", relations.get(1).getFrom());
    Assertions.assertEquals("char4", relations.get(1).getTo());
    Assertions.assertEquals("char1", relations.get(2).getFrom());
    Assertions.assertEquals("char5", relations.get(2).getTo());
    Assertions.assertEquals("char3", relations.get(3).getFrom());
    Assertions.assertEquals("char5", relations.get(3).getTo());
  }

  @Test
  public void testConsecutiveMultiplePlayersAscendingOrder() {
    // Player 4 login and player 1 logout
    // Player 5 login and player 3 logout
    // Result: char1 -> char4, char3 -> char4,  char1 -> char5, char3 -> char5

    TreeSet<String> oldPlayers = new TreeSet<>();
    oldPlayers.add("char1");
    oldPlayers.add("char2");
    oldPlayers.add("char3");

    TreeSet<String> newPlayers = new TreeSet<>();
    newPlayers.add("char2");
    newPlayers.add("char4");
    newPlayers.add("char5");

    List<Relation> relations = ConsecutiveLoginsProcessor.process(oldPlayers, newPlayers);

    Assertions.assertEquals(4, relations.size());
    Assertions.assertTrue(
        relations.stream()
            .allMatch(relation -> RelationType.CONSECUTIVE.equals(relation.getType())));
    Assertions.assertEquals("char1", relations.get(0).getFrom());
    Assertions.assertEquals("char4", relations.get(0).getTo());
    Assertions.assertEquals("char3", relations.get(1).getFrom());
    Assertions.assertEquals("char4", relations.get(1).getTo());
    Assertions.assertEquals("char1", relations.get(2).getFrom());
    Assertions.assertEquals("char5", relations.get(2).getTo());
    Assertions.assertEquals("char3", relations.get(3).getFrom());
    Assertions.assertEquals("char5", relations.get(3).getTo());
  }

  @Test
  public void testConsecutiveEmptyAllData() {
    Assertions
        .assertTrue(ConsecutiveLoginsProcessor.process(new TreeSet<>(), new TreeSet<>()).isEmpty());
  }

  @Test
  public void testConsecutiveEmptyNewData() {
    Assertions
        .assertTrue(ConsecutiveLoginsProcessor
            .process(new TreeSet<>(Collections.singleton("a")),
                new TreeSet<>()).isEmpty());
  }

  @Test
  public void testConsecutiveEmptyOldData() {
    Assertions
        .assertTrue(ConsecutiveLoginsProcessor
            .process(
                new TreeSet<>(), new TreeSet<>(Collections.singleton("a")))
            .isEmpty());
  }
}

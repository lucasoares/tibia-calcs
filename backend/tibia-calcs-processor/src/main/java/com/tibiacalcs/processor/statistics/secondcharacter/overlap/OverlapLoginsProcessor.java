package com.tibiacalcs.processor.statistics.secondcharacter.overlap;

import com.google.common.collect.Sets;
import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.Relation;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.RelationType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class OverlapLoginsProcessor {

  public List<Relation> process(SortedSet<String> playersIsOnline) {
    Timer timer = new Timer();

    if (playersIsOnline.size() <= 1) {
      return Collections.emptyList();
    }

    @SuppressWarnings("UnstableApiUsage")
    Set<Set<String>> overlaps = Sets
        .combinations(playersIsOnline, 2);

    List<Relation> relationList = new ArrayList<>();
    for (Set<String> players : overlaps) {
      Iterator<String> iterator = players.iterator();
      String from = iterator.next();
      String to = iterator.next();

      relationList
          .add(new Relation(from, to, RelationType.OVERLAP));
    }

    log.info("{} overlaps found in {}ms.", overlaps.size(), timer.elapsedMillisTime());

    return relationList;
  }

}

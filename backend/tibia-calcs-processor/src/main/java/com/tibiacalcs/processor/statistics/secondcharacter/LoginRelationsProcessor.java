/*
 * Tibia Calcs GPL Source Code
 * Copyright (C) 2020 Lucas Soares de Miranda
 * Copyright (C) 2020 Luiz Claudio Soares de Miranda
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
 */
package com.tibiacalcs.processor.statistics.secondcharacter;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.google.common.annotations.VisibleForTesting;
import com.mongodb.bulk.BulkWriteResult;
import com.tibiacalcs.core.relation.PlayerLoginRelation;
import com.tibiacalcs.core.timer.Timer;
import com.tibiacalcs.processor.statistics.secondcharacter.consecutive.ConsecutiveLoginsProcessor;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.Relation;
import com.tibiacalcs.processor.statistics.secondcharacter.entities.RelationType;
import com.tibiacalcs.processor.statistics.secondcharacter.overlap.OverlapLoginsProcessor;
import com.tibiacalcs.processor.task.Task;
import com.tibiacalcs.processor.task.TaskType;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;

@Log4j2
public class LoginRelationsProcessor extends Task {

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private final TreeSet<String> playersWasOnline;

  @VisibleForTesting
  @Getter(AccessLevel.PACKAGE)
  private final TreeSet<String> playersIsOnline;

  private final MongoTemplate mongoTemplate;

  public LoginRelationsProcessor(
      @NonNull Set<String> playersWasOnline,
      @NonNull Set<String> playersIsOnline,
      @NonNull MongoTemplate mongoTemplate) {
    this.playersWasOnline = new TreeSet<>(playersWasOnline);
    this.playersIsOnline = new TreeSet<>(playersIsOnline);
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  protected TaskType getType() {
    return TaskType.SECOND_CHARACTER_FINDER;
  }

  protected void processImpl() {
    List<Relation> consecutiveLogins = ConsecutiveLoginsProcessor
        .process(this.playersWasOnline, this.playersIsOnline);
    storeRelations(consecutiveLogins);

    List<Relation> overlaps = OverlapLoginsProcessor.process(this.playersIsOnline);
    storeRelations(overlaps);
  }

  private void storeRelations(List<Relation> relations) {
    Timer timer = new Timer();

    BulkOperations bulkOperations = bulkOps();

    for (int i = 1; i <= relations.size(); i++) {
      Relation relation = relations.get(i);

      bulkOperations.upsert(
          query(where("from").is(relation.getFrom()).and("to").is(relation.getTo())),
          new Update().inc(RelationType.CONSECUTIVE.equals(relation.getType()) ? "consecutiveCount"
              : "overlapCount", 1));

      if (i % 5000 != 0) {
        continue;
      }

      executeBulkOperation(bulkOperations);

      bulkOperations = bulkOps();
    }

    if (relations.size() % 5000 != 0) {
      executeBulkOperation(bulkOperations);
    }

    log.debug("{}s to save all consecutive logins.", timer.elapsedTime(TimeUnit.SECONDS));
  }

  private void executeBulkOperation(BulkOperations bulkOperations) {
    try {
      BulkWriteResult result = bulkOperations.execute();

      log.info("Matched: {}, Updated: {}, Upserts: {}", result.getMatchedCount(),
          result.getModifiedCount(), result.getUpserts().size());
    } catch (Exception e) {
      log.warn("Error executing bulk operations.", e);
    }
  }

  private BulkOperations bulkOps() {
    return this.mongoTemplate
        .bulkOps(BulkMode.UNORDERED, PlayerLoginRelation.class);
  }
}

package com.tibiacalcs.discordbot.commands.impl.online;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import com.tibiacalcs.core.events.impl.login.LogOutEvent;
import com.tibiacalcs.core.player.Player;
import com.tibiacalcs.core.player.PlayerRepository;
import com.tibiacalcs.discordbot.commands.Command;
import com.tibiacalcs.discordbot.commands.utils.CommandUtils;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component("online")
public class OnlineTimeCommand implements Command {

  private final Date HONBRA_COLLECT_DATE = new Date(1595568215000L);
  private final MongoTemplate mongoTemplate;
  private final PlayerRepository playerRepository;

  @Override
  public String getDescription() {
    return """
        Checks for total online time for a character. Data collected since 24 Jul 2020.""";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc online Agent Chase");
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {

    final MessageChannel channel = event.getMessage().getChannel().block();

    Player player = this.playerRepository
        .findByNameRegex(content);

    if (player == null) {
      CommandUtils.sendPlayerNotFound(channel, content);

      return;
    }

    log.debug("Searching for online time of " + content);

    try {
      Duration durationLastMonth = getOnlineTime(player,
          new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)));

      Duration durationTotal = getOnlineTime(player, this.HONBRA_COLLECT_DATE);

      channel.createEmbed(spec -> spec.setColor(Color.GREEN)
          .setDescription("""
              Character online time. It is an approximate value.

              We are collecting data since 24 Jul 2020.
              We are collecting data only from Honbra right now.

              For more information access https://tibiacalcs.com

              **Name**: %s
              **Vocation**: %s
              **Level**: %s
              **World**: %s
              **Online time last 30 days**: %s hours
              **Online time since 24 Jul 2020**: %s hours
              """
              .formatted(player.getName(), player.getVoc(), player.getLvl(), player.getWorld(),
                  durationLastMonth.toHours(), durationTotal.toHours()))
          .setTitle(
              "Online Time")
          .setFooter("© TibiaCalcs", "")
          .setUrl("https://tibiacalcs.com/discordbot")
      ).block();

    } catch (Exception e) {
      log.error("Error getting online time.", e);

      CommandUtils.sendPlayerNotFound(channel, content);
    }
  }

  public Duration getOnlineTime(Player player, Date date) {
    Aggregation aggregation = newAggregation(
        match(
            new Criteria("in").gte(date)
                .and("name").is(player.getName())),
        project().and("$date").minus("$in").as("total"),
        group().sum("total").as("count")
    );

    AggregationResults<Document> aggregate = this.mongoTemplate
        .aggregate(aggregation, this.mongoTemplate.getCollectionName(LogOutEvent.class),
            Document.class);

    return Duration.ofMillis(aggregate.getMappedResults().get(0).getLong("count"));
  }
}

package com.tibiacalcs.discordbot.commands.impl.secondcharacter;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.tibiacalcs.core.player.Player;
import com.tibiacalcs.core.player.PlayerRepository;
import com.tibiacalcs.core.relation.PlayerLoginRelation;
import com.tibiacalcs.discordbot.commands.Command;
import com.tibiacalcs.discordbot.commands.impl.onlinetime.OnlineTimeCommand;
import com.tibiacalcs.discordbot.commands.utils.CommandUtils;
import com.tibiacalcs.discordbot.exceptions.InvalidCommandParameterException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component("second")
public class SecondCharacterCommand implements Command {

  private final MongoTemplate mongoTemplate;
  private final PlayerRepository playerRepository;
  private final OnlineTimeCommand onlineTimeCommand;

  @Override
  public String getDescription() {
    return """
        Searches for possible second characters on Honbra. Data collected from Honbra since 24 Jul 2020.""";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc second Agent Chase");
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {
    if (content.isEmpty()) {
      throw new InvalidCommandParameterException();
    }

    Player player = this.playerRepository
        .findByName(content);

    final MessageChannel channel = event.getMessage().getChannel().block();

    if (player == null) {
      CommandUtils.sendPlayerNotFound(channel, content);

      return;
    }

    log.debug("Searching for second character data of " + content);

    List<PlayerLoginRelation> playerLoginRelations = getRelations(player);

    if (playerLoginRelations.isEmpty()) {
      CommandUtils.sendPlayerNotFound(channel, player.getName());

      return;
    }

    Date lastThirtyDays =
        new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));

    Map<String, Player> players = getPlayerData(player, playerLoginRelations).stream().collect(
        Collectors.toMap(Player::getName, Function.identity()));
    channel.createEmbed(spec -> {
          EmbedCreateSpec embedCreateSpec = spec.setColor(Color.GRAY)
              .setDescription("""
                  List of possible second characters.
                  There is no guarantee that characters displayed here are from the same player.
                  Only top 10 characters with score 5 or higher are shown.

                  We are collecting data only from Honbra right now.
                  For more information access https://tibiacalcs.com

                  **Name**: %s
                  **Vocation**: %s
                  **Level**: %s
                  **World**: %s
                  **Online time last 30 days**: %s hours
                  """
                  .formatted(player.getName(), player.getVoc(), player.getLvl(), player.getWorld(),
                      this.onlineTimeCommand
                          .getOnlineTime(player, lastThirtyDays)
                          .toHours()))
              .setTitle(
                  "Second Characters")
              .setFooter("© TibiaCalcs", "")
              .setUrl("https://tibiacalcs.com/discordbot");

          for (PlayerLoginRelation relation : playerLoginRelations) {
            String name = relation.getFrom();
            if (name.equals(player.getName())) {
              name = relation.getTo();
            }

            Player relationPlayer = players.get(name);

            embedCreateSpec
                .addField(name + " (Score: " + relation.getConsecutiveCount() + ")",
                    """
                        Vocation: %s
                        Level: %s
                        World: %s
                        Online time last 30 days: %s hours
                        """.formatted(relationPlayer.getVoc(),
                        relationPlayer.getLvl(), relationPlayer.getWorld(), this.onlineTimeCommand
                            .getOnlineTime(relationPlayer, lastThirtyDays)
                            .toHours()),
                    false);
          }
        }
    ).block();
  }

  private List<Player> getPlayerData(Player player,
      List<PlayerLoginRelation> playerLoginRelations) {
    Set<String> players = new HashSet<>();
    for (PlayerLoginRelation relation : playerLoginRelations) {
      players.add(relation.getFrom());
      players.add(relation.getTo());
    }

    players.remove(player.getName());

    return this.playerRepository.findAllByNameIn(players);
  }

  private List<PlayerLoginRelation> getRelations(Player player) {
    return this.mongoTemplate.find(query(
        new Criteria()
            .orOperator(where("from").is(player.getName()), where("to").is(player.getName()))
            .and("overlapCount").is(null).and("consecutiveCount").gte(5))
            .with(Sort.by("consecutiveCount").descending()).limit(10),
        PlayerLoginRelation.class);
  }
}

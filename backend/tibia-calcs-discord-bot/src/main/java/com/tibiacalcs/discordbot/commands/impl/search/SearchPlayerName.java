package com.tibiacalcs.discordbot.commands.impl.search;

import com.tibiacalcs.core.player.Player;
import com.tibiacalcs.core.player.PlayerRepository;
import com.tibiacalcs.discordbot.commands.Command;
import com.tibiacalcs.discordbot.commands.impl.online.OnlineTimeCommand;
import com.tibiacalcs.discordbot.commands.utils.CommandUtils;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component("search")
public class SearchPlayerName implements Command {

  private final PlayerRepository playerRepository;
  private final OnlineTimeCommand onlineTimeCommand;

  @Override
  public String getDescription() {
    return """
        Searches for top 10 players with similar names.
        Only players that logged in at least one time after 24 Jul 2020 will be displayed.""";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc search agent");
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {
    final MessageChannel channel = event.getMessage().getChannel().block();

    List<Player> players = this.playerRepository
        .findAllByNameRegexOrderByLvlDesc(content);

    if (players == null || players.isEmpty()) {
      CommandUtils.sendPlayerNotFound(channel, content);

      return;
    }

    Date lastThirtyDays =
        new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30));

    channel.createEmbed(spec -> {
          EmbedCreateSpec embedCreateSpec = spec.setColor(Color.GREEN)
              .setDescription("""
                  List of characters with similar names.

                  Only the top 10 players that logged in at least one time after 24 Jul 2020 will be displayed.
                  We are collecting data only from Honbra right now.
                  For more information access https://tibiacalcs.com
                  """
              )
              .setTitle(
                  "Similar Character Names")
              .setFooter("© TibiaCalcs", "")
              .setUrl("https://tibiacalcs.com/discordbot");

          for (Player player : players) {
            embedCreateSpec
                .addField(player.getName(),
                    """
                        Vocation: %s
                        Level: %s
                        World: %s
                        Online time last 30 days: %s hours
                        """.formatted(player.getVoc(),
                        player.getLvl(), player.getWorld(), this.onlineTimeCommand
                            .getOnlineTime(player, lastThirtyDays)
                            .toHours()),
                    false);
          }
        }
    ).block();
  }
}

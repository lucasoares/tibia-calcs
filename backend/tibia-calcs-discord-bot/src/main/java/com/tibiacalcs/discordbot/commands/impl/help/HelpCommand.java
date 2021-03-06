package com.tibiacalcs.discordbot.commands.impl.help;

import com.tibiacalcs.discordbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("help")
@RequiredArgsConstructor
public class HelpCommand implements Command {

  private final Map<String, Command> commands;

  @Override
  public String getDescription() {
    return "Displays all available commands.";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc help");
  }

  @Override
  public boolean acceptEmptyContent() {
    return true;
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {
    final MessageChannel channel = event.getMessage().getChannel().block();

    channel.createEmbed(spec -> {
          EmbedCreateSpec embedCreateSpec = spec.setColor(Color.GREEN)
              .setDescription(
                  "List of all available commands.\nFor more information access https://tibiacalcs.com")
              .setTitle("Available Commands")
              .setFooter("© TibiaCalcs", "")
              .setUrl("https://tibiacalcs.com/discordbot");

          for (Entry<String, Command> entry : this.commands
              .entrySet()) {

            Command command = entry.getValue();
            embedCreateSpec
                .addField(entry.getKey(),
                    command.getDescription() + "\n`!tc " + entry.getKey()
                        + " help`",
                    false);
          }
        }
    ).block();
  }

}

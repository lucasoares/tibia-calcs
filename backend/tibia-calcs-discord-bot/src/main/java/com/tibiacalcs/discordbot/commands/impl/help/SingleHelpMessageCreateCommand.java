package com.tibiacalcs.discordbot.commands.impl.help;

import com.tibiacalcs.discordbot.commands.MessageCreateCommand;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SingleHelpMessageCreateCommand {

  // Injecting all Commands as a strategy map
  private final Map<String, MessageCreateCommand> commands;

  public void showHelp(MessageChannel channel, String commandName) {
    MessageCreateCommand command = commands.get(commandName);

    StringBuilder helperMessage = new StringBuilder(command.getDescription())
        .append("\n\nExamples:");

    for (String example : command.getExamples()) {
      helperMessage.append("\n`").append(example).append("`");
    }

    channel.createEmbed(spec ->
        spec.setColor(Color.GRAY)
            .setDescription(helperMessage.toString())
            .setTitle("Command Helper: " + commandName)
            .setFooter("© TibiaCalcs", "")
            .setUrl("https://tibiacalcs.com/discordbot")
    ).block();
  }

}

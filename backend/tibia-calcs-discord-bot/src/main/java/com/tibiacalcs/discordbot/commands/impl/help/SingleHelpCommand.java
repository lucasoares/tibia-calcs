package com.tibiacalcs.discordbot.commands.impl.help;

import com.tibiacalcs.discordbot.commands.Command;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SingleHelpCommand {

  // Injecting all Commands as a strategy map
  private final Map<String, Command> commands;

  public void showHelp(MessageChannel channel, String commandName) {
    Command command = this.commands.get(commandName);

    StringBuilder helperMessage = new StringBuilder(command.getDescription())
        .append("\n\nExamples:");

    for (String example : command.getExamples()) {
      helperMessage.append("\n`").append(example).append("`");
    }

    channel.createEmbed(spec ->
        spec.setColor(Color.GREEN)
            .setDescription(helperMessage.toString())
            .setTitle("Command Helper: " + commandName)
            .setFooter("© TibiaCalcs", "")
            .setUrl("https://tibiacalcs.com/discordbot")
    ).block();
  }

}

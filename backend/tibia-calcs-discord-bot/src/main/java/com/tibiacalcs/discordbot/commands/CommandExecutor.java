package com.tibiacalcs.discordbot.commands;

import com.tibiacalcs.discordbot.commands.impl.help.SingleHelpMessageCreateCommand;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {

  // Injecting all Commands as a strategy map
  private final Map<String, MessageCreateCommand> commands;
  private final SingleHelpMessageCreateCommand helper;

  public CommandExecutor(GatewayDiscordClient gateway,
      Map<String, MessageCreateCommand> commands, SingleHelpMessageCreateCommand helper) {
    this.commands = commands;
    this.helper = helper;

    gateway.on(MessageCreateEvent.class)
        .subscribe(this::execute);
  }

  private void execute(MessageCreateEvent event) {
    String content = event.getMessage().getContent();
    if (!content.startsWith("!tc")) {
      return;
    }

    String[] split = content.split(" ");

    if (split.length == 1) {
      this.commands.get("help").consume(event, null);

      return;
    }

    String typedCommand = content.split(" ")[1];
    MessageCreateCommand messageCreateCommand = commands.get(typedCommand);

    if (messageCreateCommand == null) {
      this.commands.get("help").consume(event, null);

      return;
    }

    String commandContent = content.replace("!tc " + typedCommand + " ", "");

    if (commandContent.startsWith("help")) {
      helper
          .showHelp(event.getMessage().getChannel().block(), typedCommand);

      return;
    }

    messageCreateCommand.consume(event, commandContent);
  }
}

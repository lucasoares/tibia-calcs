package com.tibiacalcs.discordbot.commands;

import com.tibiacalcs.discordbot.commands.impl.help.SingleHelpCommand;
import com.tibiacalcs.discordbot.commands.utils.CommandUtils;
import com.tibiacalcs.discordbot.exceptions.InvalidCommandParameterException;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@Log4j2
@Component
public class CommandExecutor {

  private static final long ACCEPTED_USER = 146773891577020416L;
  private static final long ACCEPTED_ROLE = 717430112097665217L;
  private static final List<Long> ACCEPTED_CHANNELS = Arrays
      .asList(698902548370685953L, 725533567815385110L);

  // Injecting all Commands as a strategy map
  private final Map<String, Command> commands;
  private final SingleHelpCommand helper;

  public CommandExecutor(GatewayDiscordClient gateway,
      Map<String, Command> commands, SingleHelpCommand helper) {
    this.commands = commands;
    this.helper = helper;

    gateway.on(MessageCreateEvent.class).doOnEach(this::execute)
        .onErrorResume(e -> {
          logError(e);

          return Mono.empty();
        }).retry().subscribe();
  }

  private void logError(Throwable a) {
    log.error("Error executing command.", a);
  }

  private void execute(Signal<MessageCreateEvent> signal) {
    try {
      execute(signal.get());
    } catch (Exception e) {
      logError(e);
    }
  }

  private void execute(MessageCreateEvent event) {
    MessageChannel channel = event.getMessage().getChannel().block();

    if (!ACCEPTED_CHANNELS.contains(channel.getId().asLong())) {
      return;
    }

    String content = event.getMessage().getContent();
    if (!content.startsWith("!tc")) {
      return;
    }

    Optional<Member> member = event.getMember();

    if (member.isEmpty()) {
      CommandUtils.sendInvalidPermissions(channel);

      return;
    }

    if (!member.get().getRoleIds().contains(Snowflake.of(ACCEPTED_ROLE))
        && member.get().getId().asLong() != ACCEPTED_USER) {
      CommandUtils.sendInvalidPermissions(channel);

      return;
    }

    String[] split = content.split(" ");

    if (split.length == 1) {
      this.commands.get("help").consume(event, null);

      return;
    }

    String typedCommand = content.split(" ")[1];
    Command command = this.commands.get(typedCommand);

    if (command == null) {
      this.commands.get("help").consume(event, null);

      return;
    }

    String commandContent = CommandUtils
        .getNormalizedString(content.replace("!tc " + typedCommand + " ", ""));

    if (commandContent.startsWith("help")) {
      this.helper
          .showHelp(channel, typedCommand);

      return;
    }

    if (commandContent.isEmpty() && !command
        .acceptEmptyContent()) {
      throw new InvalidCommandParameterException();
    }

    command.consume(event, commandContent);
  }
}

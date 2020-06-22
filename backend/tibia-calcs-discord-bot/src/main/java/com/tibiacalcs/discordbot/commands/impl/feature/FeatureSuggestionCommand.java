package com.tibiacalcs.discordbot.commands.impl.feature;

import com.tibiacalcs.discordbot.commands.MessageCreateCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component("feature")
@RequiredArgsConstructor
public class FeatureSuggestionCommand implements MessageCreateCommand {

  @Override
  public String getDescription() {
    return "Sends a feature suggestion for Tibia Calcs developers.";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc feature A command to make money would be nice!");
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {
    User author = event.getMessage().getAuthor().orElse(null);

    log.info("User {} feature request: {}", author == null ? "anonymous" : author.getTag(),
        content);

    if (author == null) {
      return;
    }

    event.getMessage().getChannel().block().createMessage(
        messageCreateSpec -> messageCreateSpec.setContent(
            author.getMention() + " your feature request was registered!"))
        .block();
  }

}

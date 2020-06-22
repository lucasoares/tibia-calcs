package com.tibiacalcs.discordbot.commands.impl.secondcharacter;

import com.tibiacalcs.discordbot.commands.MessageCreateCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component("second")
public class SecondCharacterMessageCreateCommand implements MessageCreateCommand {

  @Override
  public String getDescription() {
    return "Searches for possible second characters.";
  }

  @Override
  public List<String> getExamples() {
    return Collections.singletonList("!tc second Agent Chase");
  }

  @Override
  public void consume(MessageCreateEvent event, String content) {

  }
}

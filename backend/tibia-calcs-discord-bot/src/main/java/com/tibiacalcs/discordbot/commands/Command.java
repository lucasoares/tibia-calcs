package com.tibiacalcs.discordbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.List;

public interface Command {

  String getDescription();

  List<String> getExamples();

  void consume(MessageCreateEvent event, String content);

  default boolean acceptEmptyContent() {
    return false;
  }
}

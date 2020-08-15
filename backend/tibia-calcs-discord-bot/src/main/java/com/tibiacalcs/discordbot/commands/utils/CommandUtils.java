package com.tibiacalcs.discordbot.commands.utils;

import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtils {

  public String getNormalizedString(String content) {
    return content.trim().replace("\"", "").replace(",", "");
  }

  public void sendPlayerNotFound(MessageChannel channel, String normalizedName) {
    channel.createEmbed(spec -> spec.setColor(Color.GRAY)
        .setDescription(
            """
                There is not enough data for `%s`.
                We need more time to collect data from this player.

                We are collecting data only from Honbra right now.
                For more information access https://tibiacalcs.com
                """.formatted(normalizedName))
        .setTitle("Not Found")
        .setFooter("© TibiaCalcs", "")
        .setUrl("https://tibiacalcs.com/discordbot")).block();
  }

  public void sendInvalidPermissions(MessageChannel channel) {
    channel.createEmbed(spec -> spec.setColor(Color.GRAY)
        .setDescription(
            """
                You do not have enough permission to execute this command

                For more information access https://tibiacalcs.com or contact the server administrator.
                """)
        .setTitle("Invalid Permission")
        .setFooter("© TibiaCalcs", "")
        .setUrl("https://tibiacalcs.com/discordbot")).block();
  }
}

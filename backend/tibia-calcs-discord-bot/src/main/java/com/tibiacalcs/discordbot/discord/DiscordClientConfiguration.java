package com.tibiacalcs.discordbot.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.response.ResponseFunction;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class DiscordClientConfiguration implements DisposableBean {

  private GatewayDiscordClient gateway;

  @Bean
  public DiscordClient buildClient(@Value("${tibiacalcs.discord-bot.token:#null}") String token) {
    return DiscordClient.builder(token).onClientResponse(ResponseFunction.emptyIfNotFound())
        .build();
  }

  @Bean
  public GatewayDiscordClient buildGateway(DiscordClient client) {
    this.gateway = client.login().block();

    return this.gateway;
  }

  @Override
  public void destroy() {
    this.gateway.logout().block();
  }
}

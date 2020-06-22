package com.tibiacalcs.discordbot.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordClientConfiguration implements DisposableBean {

  private GatewayDiscordClient gateway;

  @Bean
  public DiscordClient buildClient(@Value("${tibiacalcs.discord-bot.token:#null}") String token) {
    return DiscordClient.create(token);
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

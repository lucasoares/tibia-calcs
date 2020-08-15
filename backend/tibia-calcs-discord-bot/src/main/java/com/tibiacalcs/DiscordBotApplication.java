package com.tibiacalcs;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DiscordBotApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DiscordBotApplication.class).bannerMode(Mode.OFF).run(args);
  }
}

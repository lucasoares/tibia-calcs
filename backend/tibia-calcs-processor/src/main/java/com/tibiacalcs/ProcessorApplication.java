package com.tibiacalcs;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProcessorApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ProcessorApplication.class).bannerMode(Mode.OFF).run(args);
  }
}

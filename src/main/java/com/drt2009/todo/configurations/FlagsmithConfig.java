package com.drt2009.todo.configurations;

import com.flagsmith.FlagsmithClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlagsmithConfig {

  @Value("${flagsmith.apiKey}")
  private String flagsmithApiKey;

  @Bean
  public FlagsmithClient flagsmithClient() {
    return FlagsmithClient
        .newBuilder()
        .setApiKey(flagsmithApiKey)
        .build();

  }
}
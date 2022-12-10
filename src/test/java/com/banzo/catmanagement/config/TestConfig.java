package com.banzo.catmanagement.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

  @Bean
  @Primary
  Clock fixedClock() {
    return Clock.fixed(Instant.parse("2022-12-31T23:15:30.00Z"), ZoneId.of("UTC"));
  }
}

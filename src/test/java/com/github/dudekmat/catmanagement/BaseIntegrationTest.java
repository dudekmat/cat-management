package com.github.dudekmat.catmanagement;

import com.github.dudekmat.catmanagement.config.TestConfig;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@ContextConfiguration(initializers = BaseIntegrationTest.Initializer.class)
@ActiveProfiles("integrationTest")
@Import(TestConfig.class)
@WithMockUser(username="admin",roles={"VIEWER","ADMIN"})
public abstract class BaseIntegrationTest {

  private static final PostgreSQLContainer postgreSQLContainer =
      new PostgreSQLContainer("postgres:latest")
          .withUsername("postgres")
          .withPassword("password")
          .withDatabaseName("cat-food");

  public static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      String[] properties = {
          "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreSQLContainer.getUsername(),
          "spring.datasource.password=" + postgreSQLContainer.getPassword(),
          "spring.datasource.jdbcUrl=" + postgreSQLContainer.getJdbcUrl(),
      };
      TestPropertyValues.of(properties).applyTo(configurableApplicationContext.getEnvironment());
    }

    static {
      postgreSQLContainer.start();
    }
  }
}
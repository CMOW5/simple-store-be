package com.cristian.simplestore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.cristian.simplestore.infrastructure.config.properties.AuthProperties;
import com.cristian.simplestore.infrastructure.config.properties.StorageConfig;
import com.cristian.simplestore.infrastructure.dbseeders.CategorySeeder;
import com.cristian.simplestore.infrastructure.storage.ImageStorageService;

@SpringBootApplication
@EnableConfigurationProperties({StorageConfig.class, AuthProperties.class})
public class SimpleStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleStoreApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(
      ImageStorageService storageService, CategorySeeder categorySeeder) {
    return args -> {
      categorySeeder.seed(100);
      // productSeeder.seed(200);
      storageService.init();
    };

  }
}


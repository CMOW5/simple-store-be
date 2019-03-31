package com.cristian.simplestore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.cristian.simplestore.business.services.storage.ImageStorageService;
import com.cristian.simplestore.business.services.storage.StorageConfig;
import com.cristian.simplestore.persistence.database.seeders.CategorySeeder;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class SimpleStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(SimpleStoreApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(CategorySeeder categorySeeder, ImageStorageService storageService) {
    return (args) -> {
      // categorySeeder.seed();
      storageService.init();
    };

  }
}


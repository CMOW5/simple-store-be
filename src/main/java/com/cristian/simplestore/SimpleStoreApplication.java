package com.cristian.simplestore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.cristian.simplestore.database.seeders.CategorySeeder;
import com.cristian.simplestore.storage.ImageStorageService;
import com.cristian.simplestore.storage.StorageProperties;
import com.cristian.simplestore.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SimpleStoreApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleStoreApplication.class);

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


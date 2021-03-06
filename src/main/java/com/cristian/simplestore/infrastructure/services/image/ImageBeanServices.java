package com.cristian.simplestore.infrastructure.services.image;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.image.services.DeleteImageService;
import com.cristian.simplestore.domain.storage.StorageService;
import com.cristian.simplestore.infrastructure.database.image.ImageRepositoryJpa;
import com.cristian.simplestore.infrastructure.database.image.ImageRepositoryJpaInterface;

@Configuration
public class ImageBeanServices {
	
	@Bean
	public DeleteImageService deleteImageService(ImageRepositoryJpaInterface jpaRepo, StorageService storageService) {
		ImageRepositoryJpa imageRepository = new ImageRepositoryJpa(jpaRepo);
		return new DeleteImageService(imageRepository);
	}
}

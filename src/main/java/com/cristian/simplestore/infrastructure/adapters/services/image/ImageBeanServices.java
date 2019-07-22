package com.cristian.simplestore.infrastructure.adapters.services.image;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.image.DeleteImageService;
import com.cristian.simplestore.domain.services.storage.StorageService;
import com.cristian.simplestore.infrastructure.adapters.repository.image.ImageRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.image.ImageRepositoryJpaInterface;

@Configuration
public class ImageBeanServices {
	@Bean
	public CreateImageService createImageService(ImageRepositoryJpaInterface jpaRepo, StorageService storageService) {
		ImageRepositoryJpa imageRepository = new ImageRepositoryJpa(jpaRepo);
		return new CreateImageService(imageRepository, storageService);
	}

	@Bean
	public DeleteImageService deleteImageService(ImageRepositoryJpaInterface jpaRepo, StorageService storageService) {
		ImageRepositoryJpa imageRepository = new ImageRepositoryJpa(jpaRepo);
		return new DeleteImageService(imageRepository);
	}
}

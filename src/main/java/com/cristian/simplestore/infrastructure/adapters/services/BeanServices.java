package com.cristian.simplestore.infrastructure.adapters.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.image.DeleteImageService;
import com.cristian.simplestore.domain.services.product.CreateProductService;
import com.cristian.simplestore.domain.services.storage.StorageService;
import com.cristian.simplestore.infrastructure.adapters.repository.ImageRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.ImageRepositoryJpaInterface;
import com.cristian.simplestore.infrastructure.adapters.repository.ProductRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.ProductRepositoryJpaInterface;

@Configuration
public class BeanServices {
    
  @Bean
  public CreateProductService createProductService(ProductRepositoryJpaInterface jpaRepo) {
    return new CreateProductService(new ProductRepositoryJpa(jpaRepo));
  }
  
  @Bean
  public CreateImageService createImageService(ImageRepositoryJpaInterface jpaRepo, StorageService storageService) {
    ImageRepositoryJpa imageRepository = new ImageRepositoryJpa(jpaRepo);
    // StorageService storageService = new Ima
    return new CreateImageService(imageRepository, storageService);

  }
  
  @Bean
  public DeleteImageService deleteImageService(ImageRepositoryJpaInterface jpaRepo, StorageService storageService) {
    ImageRepositoryJpa imageRepository = new ImageRepositoryJpa(jpaRepo);
    // StorageService storageService = new Ima
    return new DeleteImageService(imageRepository);

  }
}

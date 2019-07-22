package com.cristian.simplestore.infrastructure.adapters.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.ports.repository.ImageRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ImageEntity;

@Component
public class ImageRepositoryJpa implements ImageRepository {

  private final ImageRepositoryJpaInterface jpaRepo;
  
  @Autowired
  public ImageRepositoryJpa(ImageRepositoryJpaInterface jpaRepo) {
    this.jpaRepo = jpaRepo;
  }
  
  @Override
  public Image save(Image image) {
    ImageEntity savedEntity = jpaRepo.save(ImageEntity.fromDomain(image));
    return ImageEntity.toDomain(savedEntity);
  }

	@Override
	public void delete(Image image) {
		jpaRepo.delete(ImageEntity.fromDomain(image));
	}

}

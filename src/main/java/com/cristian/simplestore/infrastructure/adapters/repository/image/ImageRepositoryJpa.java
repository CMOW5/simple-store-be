package com.cristian.simplestore.infrastructure.adapters.repository.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.image.repository.ImageRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ImageEntity;

@Repository
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

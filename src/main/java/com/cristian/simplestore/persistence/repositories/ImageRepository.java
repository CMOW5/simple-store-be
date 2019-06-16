package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import com.cristian.simplestore.persistence.entities.ImageEntity;

public interface ImageRepository extends CrudRepository<ImageEntity, Long> {

}

package com.cristian.simplestore.persistence.respositories;

import org.springframework.data.repository.CrudRepository;
import com.cristian.simplestore.persistence.entities.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}

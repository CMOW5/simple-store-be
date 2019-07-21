package com.cristian.simplestore.infrastructure.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ImageEntity;

public interface ImageRepositoryJpaInterface extends JpaRepository<ImageEntity, Long> {

}

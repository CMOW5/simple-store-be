package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.persistence.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}

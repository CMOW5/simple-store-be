package com.cristian.simplestore.infrastructure.adapters.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ProductEntity;

public interface ProductRepositoryJpaInterface extends JpaRepository<ProductEntity, Long> {

  ProductEntity findByName(String name);

  boolean existsByName(String name);

}

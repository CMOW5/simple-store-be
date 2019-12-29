package com.cristian.simplestore.infrastructure.database.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJpaInterface extends JpaRepository<ProductEntity, Long> {

  ProductEntity findByName(String name);

  boolean existsByName(String name);

}

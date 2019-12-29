package com.cristian.simplestore.infrastructure.database.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositorySpringJpa extends JpaRepository<CategoryEntity, Long>{
  Optional<CategoryEntity> findByName(String name);
}

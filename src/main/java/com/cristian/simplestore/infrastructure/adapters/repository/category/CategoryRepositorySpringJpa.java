package com.cristian.simplestore.infrastructure.adapters.repository.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.CategoryEntity;

public interface CategoryRepositorySpringJpa extends JpaRepository<CategoryEntity, Long>{
  Optional<CategoryEntity> findByName(String name);
}

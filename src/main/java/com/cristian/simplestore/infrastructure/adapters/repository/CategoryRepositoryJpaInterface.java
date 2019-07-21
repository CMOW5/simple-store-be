package com.cristian.simplestore.infrastructure.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.CategoryEntity;

public interface CategoryRepositoryJpaInterface extends JpaRepository<CategoryEntity, Long>{
  CategoryEntity findByName(String name);
}

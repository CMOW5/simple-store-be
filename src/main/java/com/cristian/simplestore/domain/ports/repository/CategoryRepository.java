package com.cristian.simplestore.domain.ports.repository;

import java.util.List;
import java.util.Optional;
import com.cristian.simplestore.domain.models.Category;

public interface CategoryRepository {
  Category save(Category category);

  boolean exists(Category category);

  Optional<Category> find(Category category);
  
  List<Category> findAll();

  Category update(Category storedCategory);

  void delete(Category category);

  void deleteAll();
}

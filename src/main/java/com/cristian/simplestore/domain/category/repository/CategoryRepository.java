package com.cristian.simplestore.domain.category.repository;

import java.util.List;
import java.util.Optional;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.pagination.Paginated;

public interface CategoryRepository {
  Category save(Category category);

  boolean exists(Category category);

  Optional<Category> find(Category category);
  
  Optional<Category> findById(Long id);
  
  List<Category> findAll();

  void delete(Category category);

  void deleteAll();

  boolean existsByName(String name);

  void deleteById(Long id);

  Paginated<Category> findAll(int page, int size);
}

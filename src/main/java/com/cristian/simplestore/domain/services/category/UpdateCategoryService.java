package com.cristian.simplestore.domain.services.category;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class UpdateCategoryService {

  private final CategoryRepository categoryRepository;

  public UpdateCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category execute(Category category) {
    return categoryRepository.update(category);
  }
}

package com.cristian.simplestore.domain.services.category;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class DeleteCategoryService {

  private final CategoryRepository categoryRepository;
  
  public DeleteCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void execute(Category category) {
    categoryRepository.delete(category);
  }

}

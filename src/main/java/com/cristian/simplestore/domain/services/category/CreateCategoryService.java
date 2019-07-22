package com.cristian.simplestore.domain.services.category;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class CreateCategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryValidator categoryValidator;
  
  public CreateCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryValidator = new CategoryValidator(categoryRepository);
  }

  public Category execute(Category category) {
	categoryValidator.validate(category);
    return categoryRepository.save(category);
  }
}

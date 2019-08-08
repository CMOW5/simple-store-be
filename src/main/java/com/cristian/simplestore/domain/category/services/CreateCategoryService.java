package com.cristian.simplestore.domain.category.services;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.CategoryValidator;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;

public class CreateCategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryValidator categoryValidator;
  
  public CreateCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryValidator = new CategoryValidator(categoryRepository);
  }

  public Category execute(Category category) {
	categoryValidator.validate(category); // TODO: were to validate
    return categoryRepository.save(category);
  }
}

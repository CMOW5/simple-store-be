package com.cristian.simplestore.domain.services.category;

import javax.persistence.EntityNotFoundException;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class UpdateCategoryService {

  private final CategoryRepository categoryRepository;

  public UpdateCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category execute(Category category) {
    Category storedCategory =
        categoryRepository.find(category).orElseThrow(() -> new EntityNotFoundException());
    storedCategory = copy(category);
    return categoryRepository.update(storedCategory);
  }

  private Category copy(Category source) {
    validateCategory(source);
    return new Category(source.getName(), source.getImage(), source.getParent());
  }

  private void validateCategory(Category category) {
    
  }

}

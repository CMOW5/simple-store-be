package com.cristian.simplestore.domain.services.category;

import javax.persistence.EntityExistsException;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class CreateCategoryService {

  private final CategoryRepository categoryRepository;
  
  public CreateCategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category execute(Category category) {
    validateCategory(category);
    return categoryRepository.save(category);
  }

  private void validateCategory(Category category) {
    validateExistance(category);
    validateData(category);
  }
  
  private void validateExistance(Category category) {
    if (categoryRepository.exists(category)) {
      throw new EntityExistsException();
    }
  }
  
  private void validateData(Category category) {
    // TODO Auto-generated method stub 
  }
}

package com.cristian.simplestore.domain.services.category;

import javax.persistence.EntityExistsException;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class CategoryValidator {

	private final CategoryRepository categoryRepository;

	public CategoryValidator(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void validate(Category category) {
		validateExistance(category);
		validateData(category);
	}

	private void validateExistance(Category category) {
		if (categoryRepository.existsByName(category.getName())) {
			throw new EntityExistsException();
		}
	}

	private void validateData(Category category) {
		
	}
}

package com.cristian.simplestore.domain.category.services;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.CategoryValidator;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.image.Image;

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

	public Category execute(String name, Image image, Long parentId) {
		Category parent = findParent(parentId);
		Category category = new Category(name, parent, image);
		categoryValidator.validate(category); // TODO: were to validate
		return categoryRepository.save(category);
	}

	private Category findParent(Long parentId) {
		Category parent = null;
		if (parentId != null) {
			parent = categoryRepository.findById(parentId).orElseThrow(() -> new CategoryNotFoundException(parentId));
		}
		return parent;
	}
}

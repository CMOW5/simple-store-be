package com.cristian.simplestore.domain.category.services;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.image.Image;

public class CreateCategoryService {

	private final CategoryRepository categoryRepository;

	public CreateCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category create(Category category) {
		return categoryRepository.save(category);
	}

	public Category create(String name, Image image, Long parentId) {
		Category parent = findParent(parentId);
		Category category = new Category(name, parent, image);
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

package com.cristian.simplestore.domain.category.services;

import com.cristian.simplestore.domain.category.repository.CategoryRepository;

public class DeleteCategoryService {

	private final CategoryRepository categoryRepository;

	public DeleteCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

}

package com.cristian.simplestore.domain.category.services;

import java.util.List;
import java.util.Optional;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.pagination.Paginated;

public class ReadCategoryService {

	private final CategoryRepository categoryRepository;

	public ReadCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Optional<Category> find(Category category) {
		return categoryRepository.find(category);
	}

	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Paginated<Category> findAll(int page, int size) {
		return categoryRepository.findAll(page, size);
	}
}

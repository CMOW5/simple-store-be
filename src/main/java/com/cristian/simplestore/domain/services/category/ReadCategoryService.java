package com.cristian.simplestore.domain.services.category;

import java.util.List;
import java.util.Optional;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;

public class ReadCategoryService {

	private final CategoryRepository categoryRepository;

	public ReadCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Optional<Category> execute(Category category) {
		return categoryRepository.find(category);
	}

	public Optional<Category> execute(Long id) {
		return categoryRepository.findById(id);
	}

	public List<Category> execute() {
		return categoryRepository.findAll();
	}

}

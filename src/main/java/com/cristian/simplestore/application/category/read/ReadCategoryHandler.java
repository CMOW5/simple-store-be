package com.cristian.simplestore.application.category.read;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.pagination.Paginated;

@Component
public class ReadCategoryHandler {

	private final ReadCategoryService readCategoryService;
	
	@Autowired
	public ReadCategoryHandler(ReadCategoryService readCategoryService) {
		this.readCategoryService = readCategoryService;
	}
	
	public List<Category> findAll() {
		return readCategoryService.findAll();
	}
	
	public Paginated<Category> findAll(int page, int size) {
		return readCategoryService.findAll(page, size);
	}
	
	public Optional<Category> findById(Long id) {
		return readCategoryService.findById(id);
	}
	
	public Paginated<Category> searchByTerm(String searchTerm, int page, int size) {
		if (searchTerm == null || searchTerm.trim().isEmpty()) {
			return readCategoryService.findAll(page, size);
		}
		return readCategoryService.searchByTerm(searchTerm, page, size);
	}
}

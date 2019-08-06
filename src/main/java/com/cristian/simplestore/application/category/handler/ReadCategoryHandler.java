package com.cristian.simplestore.application.category.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;

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
	
	public Optional<Category> findById(Long id) {
		return readCategoryService.findById(id);
	}
}

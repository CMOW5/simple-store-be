package com.cristian.simplestore.application.category.create;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.category.CategoryFactory;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.CreateCategoryService;

@Component
public class CreateCategoryHandler {

	private final CreateCategoryService createCategoryService;
	private final CategoryFactory categoryFactory;
	
	@Autowired
	public CreateCategoryHandler(CreateCategoryService service, CategoryFactory categoryFactory) {
		this.createCategoryService = service;
		this.categoryFactory = categoryFactory;
	}

	@Transactional
	public Category execute(CreateCategoryCommand command) {
		Category category = categoryFactory.create(command.getName(), command.getImage(), command.getParentId());
		return createCategoryService.execute(category); // TODO: pass the data instead of the full Category?
	}
}

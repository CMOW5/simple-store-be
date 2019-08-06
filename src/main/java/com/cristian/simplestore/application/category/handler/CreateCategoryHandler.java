package com.cristian.simplestore.application.category.handler;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.category.CategoryFactory;
import com.cristian.simplestore.application.category.command.CreateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.services.category.CreateCategoryService;

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
		return createCategoryService.execute(category);
	}
}

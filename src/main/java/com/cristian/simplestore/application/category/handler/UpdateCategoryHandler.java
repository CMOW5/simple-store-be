package com.cristian.simplestore.application.category.handler;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.category.command.UpdateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.services.category.UpdateCategoryService;

@Component
public class UpdateCategoryHandler {

	private final UpdateCategoryService updateCategoryService;

	@Autowired
	public UpdateCategoryHandler(UpdateCategoryService service) {
		this.updateCategoryService = service;
	}

	@Transactional
	public Category execute(UpdateCategoryCommand command) {
		return updateCategoryService.execute(command.getId(), command.getName(), command.getParentId(), command.getImage());
	}
}

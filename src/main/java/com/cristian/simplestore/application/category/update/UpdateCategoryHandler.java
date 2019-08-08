package com.cristian.simplestore.application.category.update;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.UpdateCategoryService;
import com.cristian.simplestore.domain.image.Image;

@Component
public class UpdateCategoryHandler {

	private final UpdateCategoryService updateCategoryService;
	private final ImageFactory ImageFactory;

	@Autowired
	public UpdateCategoryHandler(UpdateCategoryService service, ImageFactory ImageFactory) {
		this.updateCategoryService = service;
		this.ImageFactory = ImageFactory;
	}

	@Transactional
	public Category execute(UpdateCategoryCommand command) {
		Image newImage = ImageFactory.fromFile(command.getImage());
		return updateCategoryService.execute(command.getId(), command.getName(), command.getParentId(), newImage);
	}
}

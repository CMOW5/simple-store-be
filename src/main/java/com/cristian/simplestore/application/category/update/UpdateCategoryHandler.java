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
	private final ImageFactory imageFactory;

	@Autowired
	public UpdateCategoryHandler(UpdateCategoryService service, ImageFactory imageFactory) {
		this.updateCategoryService = service;
		this.imageFactory = imageFactory;
	}

	@Transactional
	public Category update(UpdateCategoryCommand command) {
		Long id = command.getId();
		String newName = command.getName();
		Long newParentId = command.getParentId();
		Image newImage = imageFactory.fromFile(command.getImage());
		return updateCategoryService.update(id, newName, newParentId, newImage);
	}
}

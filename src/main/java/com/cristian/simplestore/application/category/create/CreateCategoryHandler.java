package com.cristian.simplestore.application.category.create;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.CreateCategoryService;
import com.cristian.simplestore.domain.image.Image;

@Component
public class CreateCategoryHandler {

	private final CreateCategoryService createCategoryService;
	private final ImageFactory imageFactory;
	
	@Autowired
	public CreateCategoryHandler(CreateCategoryService service, ImageFactory imageFactory) {
		this.createCategoryService = service;
		this.imageFactory = imageFactory;
	}

	@Transactional
	public Category execute(CreateCategoryCommand command) {
		String name = command.getName();
		Long parentId = command.getParentId();
		Image image = imageFactory.fromFile(command.getImage());
		return createCategoryService.execute(name, image, parentId);
	}
}

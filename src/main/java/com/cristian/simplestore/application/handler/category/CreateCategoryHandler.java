package com.cristian.simplestore.application.handler.category;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.services.category.CreateCategoryService;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;

@Component
public class CreateCategoryHandler {
  
  private final ReadCategoryService readCategoryService;
  private final CreateCategoryService createCategoryService;
  private final CreateImageService createImageService;

  @Autowired
  public CreateCategoryHandler(CreateCategoryService service,
		  ReadCategoryService readCategoryService,
      CreateImageService createImageService) {
    this.createCategoryService = service;
    this.createImageService = createImageService;
    this.readCategoryService = readCategoryService;
  }

  public Category execute(CreateCategoryCommand command) {
    Category category = mapCommandToCategory(command);
    return createCategoryService.execute(category);
  }
  
  private Category mapCommandToCategory(CreateCategoryCommand command) {
	// TODO: validate data
    String name = command.getName();
    Category parent = readCategoryService.findById(command.getParentId()).orElseThrow(() -> new EntityNotFoundException());
    Image image = createImageService.create(command.getImage());
    return new Category(name, image, parent);
  }
}

package com.cristian.simplestore.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.application.command.UpdateCategoryCommand;
import com.cristian.simplestore.application.handler.category.CreateCategoryHandler;
import com.cristian.simplestore.application.handler.category.UpdateCategoryHandler;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.infrastructure.controllers.dto.CategoryDto;

@RestController
@RequestMapping("/api/hex/admin/categories")
public class CategoryControllerHex {
  
  private final CreateCategoryHandler createCategoryHandler;
  private final UpdateCategoryHandler updateCategoryHandler;

  
  @Autowired
  public CategoryControllerHex(CreateCategoryHandler createCategoryHandler, 
		  UpdateCategoryHandler updateCategoryHandler) {
    this.createCategoryHandler = createCategoryHandler;
    this.updateCategoryHandler = updateCategoryHandler;
  }
  
  @GetMapping
  public String hello() {
    return "hello";
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryDto create(CreateCategoryCommand command) {
    Category createdCategory = createCategoryHandler.execute(command);
    return CategoryDto.fromDomain(createdCategory);
  }
  
  @PutMapping
  public CategoryDto create(UpdateCategoryCommand command) {
    Category updatedCategory = updateCategoryHandler.execute(command);
    return CategoryDto.fromDomain(updatedCategory);
  }
}

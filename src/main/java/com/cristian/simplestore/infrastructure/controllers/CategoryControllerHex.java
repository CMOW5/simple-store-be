package com.cristian.simplestore.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.application.handler.category.CreateCategoryHandler;
import com.cristian.simplestore.domain.models.Category;

@RestController
@RequestMapping("/api/hex/admin/categories")
public class CategoryControllerHex {
  
  private final CreateCategoryHandler createCategoryHandler;
  
  @Autowired
  public CategoryControllerHex(CreateCategoryHandler createCategoryHandler) {
    this.createCategoryHandler = createCategoryHandler;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String create(CreateCategoryCommand command) {
    Category createdCategory = createCategoryHandler.execute(command);
    return "created category " + createdCategory.getName(); 
  }
}

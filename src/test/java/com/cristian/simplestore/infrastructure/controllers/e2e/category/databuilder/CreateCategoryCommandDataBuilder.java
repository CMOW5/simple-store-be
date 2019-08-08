package com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.category.command.CreateCategoryCommand;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.utils.image.MockImageFileFactory;

public class CreateCategoryCommandDataBuilder {
  private String name = "default category name";
  
  private MultipartFile image = MockImageFileFactory.createMockMultiPartFile();
  
  private Long parentId;
  
  public CreateCategoryCommandDataBuilder name(String name) {
    this.name = name;
    return this;
  }
  
  public CreateCategoryCommandDataBuilder image(MultipartFile file) {
    this.image = file;
    return this;
  }
  
  public CreateCategoryCommandDataBuilder parent(Category parent) {
    this.parentId = parent.getId();
    return this;
  }
  
  public CreateCategoryCommand build() {
    return new CreateCategoryCommand(name, image, parentId);
  }
}

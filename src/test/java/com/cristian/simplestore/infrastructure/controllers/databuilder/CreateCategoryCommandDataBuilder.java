package com.cristian.simplestore.infrastructure.controllers.databuilder;

import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;

public class CreateCategoryCommandDataBuilder {
  private String name = "default category name";
  
  private MultipartFile image = ImageFileFactory.createMockMultiPartFile();
  
  private Category parent;
  
  public CreateCategoryCommandDataBuilder name(String name) {
    this.name = name;
    return this;
  }
  
  public CreateCategoryCommandDataBuilder image(MultipartFile file) {
    this.image = file;
    return this;
  }
  
  public CreateCategoryCommandDataBuilder parent(Category parent) {
    this.parent = parent;
    return this;
  }
  
  public CreateCategoryCommand build() {
    return new CreateCategoryCommand(name, image, parent);
  }
}

package com.cristian.simplestore.infrastructure.controllers.databuilder;

import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.application.command.UpdateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;

import lombok.Data;

@Data
public class UpdateCategoryCommandDataBuilder {
  private Long id;	
	
  private String name = "default category name";
  
  private MultipartFile image = ImageFileFactory.createMockMultiPartFile();
  
  private Category parent;
  
  public UpdateCategoryCommandDataBuilder(Long id) {
	  this.id = id;
  }
  
  public UpdateCategoryCommandDataBuilder name(String name) {
    this.name = name;
    return this;
  }
  
  public UpdateCategoryCommandDataBuilder image(MultipartFile file) {
    this.image = file;
    return this;
  }
  
  public UpdateCategoryCommandDataBuilder parent(Category parent) {
    this.parent = parent;
    return this;
  }
  
  public UpdateCategoryCommand build() {
    return new UpdateCategoryCommand(id, name, image, parent);
  }
}

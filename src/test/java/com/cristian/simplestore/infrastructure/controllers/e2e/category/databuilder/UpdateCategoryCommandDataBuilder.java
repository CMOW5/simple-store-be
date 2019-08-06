package com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.category.command.UpdateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.utils.image.MockImageFileFactory;

import lombok.Data;

@Data
public class UpdateCategoryCommandDataBuilder {
  private Long id;	
	
  private String name = "default category name";
  
  private MultipartFile image = MockImageFileFactory.createMockMultiPartFile();
  
  private Long parentId;
  
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
    this.parentId = parent.getId();
    return this;
  }
  
  public UpdateCategoryCommand build() {
    return new UpdateCategoryCommand(id, name, image, parentId);
  }
}

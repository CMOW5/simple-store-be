package com.cristian.simplestore.application.command;

import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.domain.models.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryCommand {
  
  private String name;
  
  private MultipartFile image;
  
  private Category parent;

  public CreateCategoryCommand(String name, MultipartFile image, Category parent) {
    this.name = name;
    this.image = image;
    this.parent = parent;
  }
  
}

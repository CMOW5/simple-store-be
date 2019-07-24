package com.cristian.simplestore.application.command;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryCommand {
  
  private String name;
  
  private MultipartFile image;
  
  private Long parentId;

  public CreateCategoryCommand(String name, MultipartFile image, Long parentId) {
    this.name = name;
    this.image = image;
    this.parentId = parentId;
  }
  
}

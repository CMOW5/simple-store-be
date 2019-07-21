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

}

package com.cristian.simplestore.web.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;
import lombok.Data;

@ExistsDb(table = "categories", columnName = "name", columnValueField = "name")
@Data
public class CategoryCreateForm implements Form<Category> {

  @NotNull
  @Size(min = 2, max = 200)
  private String name;

  private Category parentCategory;

  private MultipartFile image;

  @Override
  public Category getModel() {
    Category category = new Category();
    category.setName(name);
    category.setParentCategory(parentCategory);
    return category;
  }
}

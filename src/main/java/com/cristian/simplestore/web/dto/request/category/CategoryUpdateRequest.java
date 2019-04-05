package com.cristian.simplestore.web.dto.request.category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.dto.request.Request;
import com.cristian.simplestore.web.validators.annotations.Exists;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;
import lombok.Data;

@ExistsDb(table = "categories", columnName = "name", columnValueField = "name",
    exceptIdColumn = "id")
@Data
public class CategoryUpdateRequest implements Request<Category> {

  @NotNull
  @Exists(table = "categories", column = "id", message = "the category doesn't exists")
  Long id;

  @NotNull
  @Size(min = 2, max = 200)
  private String name;

  private Category parentCategory;

  private MultipartFile newImage;

  private Long imageIdToDelete;

  @Override
  public Category getModel() {
    Category category = new Category();
    category.setName(name);
    category.setParentCategory(parentCategory);
    category.setId(id);
    return category;
  }

}


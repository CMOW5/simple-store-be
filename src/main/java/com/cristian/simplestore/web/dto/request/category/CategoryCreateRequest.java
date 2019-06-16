package com.cristian.simplestore.web.dto.request.category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.web.dto.request.Request;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;
import lombok.Data;

@ExistsDb(table = "categories", columnName = "name", columnValueField = "name")
@Data
public class CategoryCreateRequest implements Request<CategoryEntity> {

  @NotNull
  @Size(min = 2, max = 200)
  private String name;

  private CategoryEntity parentCategory;

  private MultipartFile image;

  @Override
  public CategoryEntity getModel() {
    CategoryEntity category = new CategoryEntity();
    category.setName(name);
    category.setParentCategory(parentCategory);
    return category;
  }
}

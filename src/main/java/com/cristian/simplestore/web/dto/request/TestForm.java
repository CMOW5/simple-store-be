package com.cristian.simplestore.web.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;

@ExistsDb(table = "categories", columnName = "name", columnValueField = "name")
public class TestForm implements Request<CategoryEntity> {

  @NotNull
  @Size(min = 2, max = 200)
  private String name;

  @NotNull
  @Size(min = 2, max = 200)
  private String description;

  @NotNull
  private CategoryEntity parentCategory;

  @NotNull
  private MultipartFile image;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryEntity getParentCategory() {
    return parentCategory;
  }

  public void setParentCategory(CategoryEntity parentCategory) {
    this.parentCategory = parentCategory;
  }

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  @Override
  public CategoryEntity getModel() {
    CategoryEntity category = new CategoryEntity();
    category.setName(name);
    category.setParentCategory(parentCategory);
    return category;
  }

}

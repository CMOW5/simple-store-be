package com.cristian.simplestore.web.dto.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponse implements ResponseEntityDto<Category> {

  private Long id;

  private String name;

  private Map<String, Object> parentCategory;

  private ImageResponse image;

  @JsonIgnore
  private Category parentCategoryObject;

  public CategoryResponse(Category category) {
    id = category.getId();
    name = category.getName();
    mapImage(category.getImage());
    mapParentCategory(category.getParentCategory());
  }

  private void mapParentCategory(Category parentCategory) {
    if (parentCategory != null) {
      this.parentCategory = new LinkedHashMap<>();
      this.parentCategory.put("id", parentCategory.getId());
      this.parentCategory.put("name", parentCategory.getName());
      this.parentCategoryObject = parentCategory;
    }
  }

  private void mapImage(Image image) {
    this.image = ImageResponse.build(image);
  }
  
  public static CategoryResponse from(Category category) {
	return new CategoryResponse(category);
  }

  public static List<CategoryResponse> from(List<Category> categories) {
    List<CategoryResponse> dtos = new ArrayList<>();
    categories.forEach(category -> dtos.add(from(category)));
    return dtos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryResponse categoryDto = (CategoryResponse) o;
    return Objects.equals(name, categoryDto.name) && Objects.equals(id, categoryDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}

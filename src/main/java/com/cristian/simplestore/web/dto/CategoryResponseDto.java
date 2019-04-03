package com.cristian.simplestore.web.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponseDto implements EntityDto<Category> {

  private Long id;

  private String name;

  private Map<String, Object> parentCategory;

  private ImageResponseDto image;

  @JsonIgnore
  private Category parentCategoryObject;

  public CategoryResponseDto(Category category) {
    id = category.getId();
    name = category.getName();
    mapImage(category.getImage());
    mapParentCategory(category.getParentCategory());
  }

  private void mapParentCategory(Category parentCategory) {
    if (parentCategory != null) {
      this.parentCategory = new LinkedHashMap<String, Object>();
      this.parentCategory.put("id", parentCategory.getId());
      this.parentCategory.put("name", parentCategory.getName());
      this.parentCategoryObject = parentCategory;
    }
  }

  private void mapImage(Image image) {
    this.image = ImageResponseDto.build(image);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryResponseDto categoryDto = (CategoryResponseDto) o;
    return Objects.equals(name, categoryDto.name) && Objects.equals(id, categoryDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}

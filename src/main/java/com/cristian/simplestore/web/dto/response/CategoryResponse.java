package com.cristian.simplestore.web.dto.response;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryResponse {

  private Long id;

  private String name;

  private ParentCategory parentCategory;

  private ImageResponse image;

  public CategoryResponse(Long id, String name, ImageResponse image, ParentCategory parentCategory) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.parentCategory = parentCategory;
  }

  public static CategoryResponse from(CategoryEntity entity) {
    CategoryResponse category = null;

    if (entity != null) {
      category = new CategoryResponse(entity.getId(), entity.getName(), ImageResponse.from(entity.getImage()),
          ParentCategory.from(entity.getParentCategory()));
    }

    return category;
  }
  
  public static List<CategoryResponse> from(List<CategoryEntity> entities) {
    List<CategoryResponse> categories = new ArrayList<>();
    
    for (CategoryEntity entity: entities) {
      categories.add(from(entity));
    }
    
    return categories;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ParentCategory {
    private long id;
    private String name;

    static ParentCategory from(CategoryEntity entity) {
      ParentCategory parent = null;

      if (entity != null) {
        parent = new ParentCategory(entity.getId(), entity.getName());
      }

      return parent;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryResponse category = (CategoryResponse) o;
    return Objects.equals(name, category.name) && Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}

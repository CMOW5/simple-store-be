package com.cristian.simplestore.web.dto.response;

import com.cristian.simplestore.persistence.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryResponseDto {
  private Long id;
  private String name;

  public static ParentCategoryResponseDto build(Category category) {
    if (category != null) {
      return new ParentCategoryResponseDto(category.getId(), category.getName());
    } else {
      return null;
    }
  }
}

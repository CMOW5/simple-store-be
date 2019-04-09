package com.cristian.simplestore.web.dto.response;

import com.cristian.simplestore.persistence.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryResponse {
  private Long id;
  private String name;

  public static ParentCategoryResponse build(Category category) {
    if (category != null) {
      return new ParentCategoryResponse(category.getId(), category.getName());
    } else {
      return null;
    }
  }
}

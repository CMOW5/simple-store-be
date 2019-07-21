package com.cristian.simplestore.infrastructure.adapters.repository.entities;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.cristian.simplestore.domain.models.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  private CategoryEntity parent;

  public CategoryEntity(String name, CategoryEntity parent) {
    this.name = name;
    this.parent = parent;
  }

  public static CategoryEntity fromDomain(Category category) {
    if (category == null)
      return null;
    String name = category.getName();
    CategoryEntity parentEntity = fromDomain(category.getParent());
    return new CategoryEntity(name, parentEntity);
  }

  public static Category toDomain(CategoryEntity entity) {
    if (entity == null)
      return null;
    String name = entity.getName();
    Category parent = toDomain(entity.getParent());
    return new Category(name, null, parent);
  }

  public static List<Category> toDomain(List<CategoryEntity> entities) {
    return entities.stream().map(CategoryEntity::toDomain).collect(Collectors.toList());
  }
}

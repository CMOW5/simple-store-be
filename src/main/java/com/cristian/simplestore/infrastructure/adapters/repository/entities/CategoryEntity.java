package com.cristian.simplestore.infrastructure.adapters.repository.entities;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
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
  
  @ManyToOne
  @JoinColumn(name = "image_id")
  private ImageEntity image;

  public CategoryEntity(String name, CategoryEntity parent, ImageEntity image) {
    this.name = name;
    this.parent = parent;
    this.image = image;
  }

  public static CategoryEntity fromDomain(Category category) {
    if (category == null)
      return null;
    String name = category.getName();
    CategoryEntity parentEntity = fromDomain(category.getParent());
    ImageEntity imageEntity = ImageEntity.fromDomain(category.getImage()); 
    return new CategoryEntity(name, parentEntity, imageEntity);
  }

  public static Category toDomain(CategoryEntity entity) {
    if (entity == null)
      return null;
    String name = entity.getName();
    Category parent = toDomain(entity.getParent());
    Image image = ImageEntity.toDomain(entity.getImage());
    return new Category(name, image, parent);
  }

  public static List<Category> toDomain(List<CategoryEntity> entities) {
    return entities.stream().map(CategoryEntity::toDomain).collect(Collectors.toList());
  }
}

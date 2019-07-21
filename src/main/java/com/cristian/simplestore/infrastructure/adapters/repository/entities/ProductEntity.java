package com.cristian.simplestore.infrastructure.adapters.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity implements Serializable {
  private static final long serialVersionUID = 8372995282951530629L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private String description;

  @Column(nullable = false)
  private double price;

  private double priceSale;

  @ColumnDefault("false")
  private boolean inSale;

  @ColumnDefault("true")
  private boolean active;

  private Long stock;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime updatedDate;
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductEntity product = (ProductEntity) o;
    return Objects.equals(name, product.name) && Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }

  public static ProductEntity fromDomain(Product product) {
    ProductEntity entity = new ProductEntity();
    entity.name = product.getName();
    entity.description = product.getDescription();
    entity.price = product.getPrice();
    entity.priceSale = product.getPriceSale();
    entity.inSale = product.isInSale();
    entity.active = product.isActive();
    // entity.category = CategoryEntity.fromDomain(product.getCategory());
    entity.stock = product.getStock();
    // entity.images = 
    return entity;
  }

  public static Product toDomain(ProductEntity entity) {
    String name = entity.getName();
    String description = entity.getDescription();
    double price = entity.getPrice();
    double priceSale = entity.getPriceSale();
    boolean inSale = entity.isInSale();
    boolean active = entity.isActive();
    Category category = null;
    // entity.category = CategoryEntity.fromDomain(product.getCategory());
    long stock = entity.getStock();
    // entity.images = 
    List<Image> images = new ArrayList<>();
    return new Product(name, description, price, priceSale, inSale, active, category, images, stock);
  }

  public static List<Product> toDomain(List<ProductEntity> entities) {
    return entities.stream().map(ProductEntity::toDomain).collect(Collectors.toList());
  }
}

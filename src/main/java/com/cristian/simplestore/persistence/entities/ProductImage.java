package com.cristian.simplestore.persistence.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
public class ProductImage implements Serializable {

  private static final long serialVersionUID = 1360932026416839388L;

  @Id
  @ManyToOne
  private ProductEntity product;

  @Id
  @ManyToOne
  private ImageEntity image;

  public ProductImage(ProductEntity productEntity, ImageEntity image) {
    this.product = productEntity;
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductImage that = (ProductImage) o;
    return Objects.equals(product, that.product) && Objects.equals(image, that.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, image);
  }
}

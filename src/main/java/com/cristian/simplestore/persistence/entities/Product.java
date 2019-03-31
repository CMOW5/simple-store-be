package com.cristian.simplestore.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

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

  @ManyToOne
  private Category category;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @Fetch(FetchMode.JOIN)
  private List<ProductImage> images = new ArrayList<>();

  private Long stock;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime updatedDate;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  public List<Image> getImages() {
    List<Image> images = new ArrayList<Image>();
    List<ProductImage> productImages = this.images;
    productImages.forEach(image -> images.add(image.getImage()));
    return images;
  }

  public void addImage(Image image) {
    ProductImage productImage = new ProductImage(this, image);
    images.add(productImage);
    image.getOwners().add(productImage);
  }

  public void addImages(List<Image> images) {
    for (Image image : images) {
      addImage(image);
    }
  }

  public void removeImage(Image image) {
    ProductImage productImage = new ProductImage(this, image);
    image.getOwners().remove(productImage);
    images.remove(productImage);
    productImage.setProduct(null);
    productImage.setImage(null);
  }

  public void removeImages(List<Image> images) {
    for (Image image : images) {
      removeImage(image);
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
    Product product = (Product) o;
    return Objects.equals(name, product.name) && Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}

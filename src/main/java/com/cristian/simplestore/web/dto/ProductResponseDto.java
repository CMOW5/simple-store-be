package com.cristian.simplestore.web.dto;

import java.util.ArrayList;
import java.util.List;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDto {

  private Long id;

  private String name;

  private String description;

  private double price;

  private double priceSale;

  private boolean inSale;

  private boolean active;

  private ParentCategoryResponseDto category;

  private List<ImageResponseDto> images = new ArrayList<>();

  private Long stock;

  public ProductResponseDto(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.priceSale = product.getPriceSale();
    this.inSale = product.isInSale();
    this.active = product.isActive();
    this.stock = product.getStock();
    mapParentCategory(product.getCategory());
    mapImages(product.getImages());
  }

  private void mapImages(List<Image> images) {
    if (images != null) {
      for (Image image : images) {
        this.images.add(new ImageResponseDto(image));
      }
    }
  }

  private void mapParentCategory(Category category) {
    if (category != null) {
      this.category = new ParentCategoryResponseDto(category.getId(), category.getName());
    }
  }
}

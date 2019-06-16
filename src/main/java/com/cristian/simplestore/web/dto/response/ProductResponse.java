package com.cristian.simplestore.web.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.web.dto.response.CategoryResponse.ParentCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {

  private Long id;

  private String name;

  private String description;

  private double price;

  private double priceSale;

  private boolean inSale;

  private boolean active;

  private ParentCategory category;

  private List<ImageResponse> images = new ArrayList<>();

  private Long stock;
  
  public static ProductResponse of(ProductEntity entity) {
    ProductResponse product = null;
    
    if (entity != null) {
      product = new ProductResponse();
      product.id = entity.getId();
      product.name = entity.getName();
      product.description = entity.getDescription();
      product.price = entity.getPrice();
      product.priceSale = entity.getPriceSale();
      product.inSale = entity.isInSale();
      product.active = entity.isActive();
      product.stock = entity.getStock();
      product.category = ParentCategory.of(entity.getCategory());
      product.images = ImageResponse.of(entity.getImages());
    }
    
    return product;
  }
  
  public static List<ProductResponse> of(List<ProductEntity> entities) {
    List<ProductResponse> products = new ArrayList<>();
    
    for (ProductEntity entity: entities) {
      products.add(of(entity));
    }
    
    return products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductResponse product = (ProductResponse) o;
    return Objects.equals(name, product.name) && Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}

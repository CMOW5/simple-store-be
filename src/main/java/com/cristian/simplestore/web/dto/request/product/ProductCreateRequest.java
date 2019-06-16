package com.cristian.simplestore.web.dto.request.product;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.web.dto.request.Request;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;
import lombok.Data;

@ExistsDb(table = "products", columnName = "name", columnValueField = "name")
@Data
public class ProductCreateRequest implements Request<ProductEntity> {

  @NotNull
  @Size(min = 2, max = 100)
  private String name;

  @Size(min = 2, max = 200)
  private String description;

  @PositiveOrZero
  private double price;

  @PositiveOrZero
  private double priceSale;

  private boolean inSale;

  private boolean active;

  private CategoryEntity category;

  private List<MultipartFile> images = new ArrayList<>();

  private Long stock;

  public void setImages(List<MultipartFile> images) {
    this.images = images != null ? images : new ArrayList<>();
  }

  @Override
  public ProductEntity getModel() {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(name);
    productEntity.setDescription(description);
    productEntity.setPrice(price);
    productEntity.setPriceSale(priceSale);
    productEntity.setInSale(inSale);
    productEntity.setActive(active);
    productEntity.setCategory(category);
    productEntity.setStock(stock);
    return productEntity;
  }
}

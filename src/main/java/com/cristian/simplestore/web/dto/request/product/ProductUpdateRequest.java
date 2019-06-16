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
import com.cristian.simplestore.web.validators.annotations.Exists;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;
import lombok.Data;

@ExistsDb(table = "products", columnName = "name", columnValueField = "name", exceptIdColumn = "id")
@Data
public class ProductUpdateRequest implements Request<ProductEntity> {

  @NotNull
  @Exists(table = "products", column = "id", message = "the product doesn't exists")
  Long id;

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

  private Long stock;

  private List<MultipartFile> newImages = new ArrayList<>();

  private List<Long> imagesIdsToDelete = new ArrayList<>();

  public void setNewImages(List<MultipartFile> newImages) {
    if (newImages == null) {
      this.newImages = new ArrayList<>();
    } else {
      this.newImages = newImages;
    }
  }

  public List<Long> getImagesIdsToDelete() {
    return imagesIdsToDelete;
  }

  public void setImagesIdsToDelete(List<Long> imagesIdsToDelete) {
    this.imagesIdsToDelete = imagesIdsToDelete;
  }

  @Override
  public ProductEntity getModel() {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setId(id);
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

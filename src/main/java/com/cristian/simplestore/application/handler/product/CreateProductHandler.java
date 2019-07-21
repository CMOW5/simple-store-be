package com.cristian.simplestore.application.handler.product;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.application.command.CreateProductCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.product.CreateProductService;

@Component
public class CreateProductHandler {

  private final CreateProductService createProductService;
  private final CreateImageService createImageService;

  @Autowired
  public CreateProductHandler(CreateProductService createProductService,
      CreateImageService createImageService) {
    this.createProductService = createProductService;
    this.createImageService = createImageService;
  }

  public Product execute(CreateProductCommand command) {
    // validate first
    Product product = mapCommandToProduct(command);
    return createProductService.execute(product);
  }

  private Product mapCommandToProduct(CreateProductCommand command) {
    String name = command.getName();
    String description = command.getDescription();
    double price = command.getPrice();
    double priceSale = command.getPriceSale();
    boolean inSale = command.isInSale();
    boolean active = command.isActive();
    Category category = command.getCategory();
    long stock = command.getStock();
    List<Image> images = createImageService.create(command.getImages());
    return new Product(name, description, price, priceSale, inSale, active, category, images,
        stock);
  }
}

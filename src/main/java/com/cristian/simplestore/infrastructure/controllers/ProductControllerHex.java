package com.cristian.simplestore.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateProductCommand;
import com.cristian.simplestore.application.handler.product.CreateProductHandler;
import com.cristian.simplestore.domain.models.Product;

@RestController
@RequestMapping("/api/hex/admin/products")
public class ProductControllerHex {
  
  private final CreateProductHandler createProductHandler;
  
  @Autowired
  public ProductControllerHex(CreateProductHandler createProductHandler) {
    this.createProductHandler = createProductHandler;
  }

  @PostMapping
  public String create(CreateProductCommand command) {
    Product createdProduct = createProductHandler.execute(command);
    return "created category " + createdProduct.getName(); 
  }
}

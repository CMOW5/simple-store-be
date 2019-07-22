package com.cristian.simplestore.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateProductCommand;
import com.cristian.simplestore.application.command.UpdateProductCommand;
import com.cristian.simplestore.application.handler.product.CreateProductHandler;
import com.cristian.simplestore.application.handler.product.UpdateProductHandler;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.infrastructure.controllers.dto.ProductDto;

@RestController
@RequestMapping("/api/hex/admin/products")
public class ProductControllerHex {
  
  private final CreateProductHandler createProductHandler;
  private final UpdateProductHandler updateProductHandler;
  
  @Autowired
  public ProductControllerHex(CreateProductHandler createProductHandler, 
		  UpdateProductHandler updateProductHandler) {
    this.createProductHandler = createProductHandler;
    this.updateProductHandler = updateProductHandler;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductDto create(CreateProductCommand command) {
    Product createdProduct = createProductHandler.execute(command);
    return ProductDto.fromDomain(createdProduct);
  }
  
  @PutMapping
  public ProductDto update(UpdateProductCommand command) {
    Product createdProduct = updateProductHandler.execute(command);
    return ProductDto.fromDomain(createdProduct);
  }
}

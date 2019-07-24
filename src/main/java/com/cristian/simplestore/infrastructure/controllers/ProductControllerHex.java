package com.cristian.simplestore.infrastructure.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateProductCommand;
import com.cristian.simplestore.application.command.UpdateProductCommand;
import com.cristian.simplestore.application.handler.product.CreateProductHandler;
import com.cristian.simplestore.application.handler.product.DeleteProductHandler;
import com.cristian.simplestore.application.handler.product.ReadProductHandler;
import com.cristian.simplestore.application.handler.product.UpdateProductHandler;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.infrastructure.controllers.dto.ProductDto;
import com.cristian.simplestore.web.utils.response.ApiResponse;

@RestController
@RequestMapping("/api/hex/admin/products")
public class ProductControllerHex {
  
  private final CreateProductHandler createProductHandler;
  private final ReadProductHandler readProductHandler;
  private final UpdateProductHandler updateProductHandler;
  private final DeleteProductHandler deleteProductHandler;
  
  @Autowired
  public ProductControllerHex(CreateProductHandler createProductHandler, 
		  ReadProductHandler readProductHandler, UpdateProductHandler updateProductHandler,
		  DeleteProductHandler deleteProductHandler) {
    this.createProductHandler = createProductHandler;
    this.readProductHandler = readProductHandler;
    this.updateProductHandler = updateProductHandler;
    this.deleteProductHandler = deleteProductHandler;
  }
  
  @GetMapping
  public ResponseEntity<?> findAll() {
    List<Product> foundProducts = readProductHandler.findAll();
    return new ApiResponse().status(HttpStatus.OK).content(ProductDto.fromDomain(foundProducts)).build();
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    Product foundProduct = readProductHandler.findById(id).orElseThrow(() -> new EntityNotFoundException());
    return new ApiResponse().status(HttpStatus.OK).content(ProductDto.fromDomain(foundProduct)).build();
  }
  
  @PostMapping
  public ResponseEntity<?> create(CreateProductCommand command) {
    Product createdProduct = createProductHandler.execute(command);
    return new ApiResponse().status(HttpStatus.CREATED).content(ProductDto.fromDomain(createdProduct)).build();
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<?> update(UpdateProductCommand command) {
    Product updatedProduct = updateProductHandler.execute(command);
    return new ApiResponse().status(HttpStatus.OK).content(ProductDto.fromDomain(updatedProduct )).build();
  }
  
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    deleteProductHandler.execute(id);
  }
  
  
}

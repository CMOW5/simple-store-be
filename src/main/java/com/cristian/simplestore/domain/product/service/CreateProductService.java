package com.cristian.simplestore.domain.product.service;

import javax.persistence.EntityExistsException;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;


public class CreateProductService {

  private final ProductRepository productRepository;

  public CreateProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product create(Product product) {
    validateProduct(product);
    return productRepository.save(product);
  }

  private void validateProduct(Product product) {
    validateExistance(product);
  }

  private void validateExistance(Product product) {
    if (productRepository.existsByName(product.getName())) {
      throw new EntityExistsException();
    }
  }
}
package com.cristian.simplestore.domain.product.service;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;

public class UpdateProductService {

  private final ProductRepository productRepository;

  public UpdateProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product) {
    return productRepository.save(product);
  }
}

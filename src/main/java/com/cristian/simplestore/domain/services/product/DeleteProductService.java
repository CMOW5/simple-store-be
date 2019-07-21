package com.cristian.simplestore.domain.services.product;

import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;

public class DeleteProductService {

  ProductRepository productRepository;

  public DeleteProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(Product product) {
    productRepository.delete(product);
  }

}

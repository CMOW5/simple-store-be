package com.cristian.simplestore.domain.services.product;

import javax.persistence.EntityExistsException;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;

public class CreateProductService {

  private final ProductRepository productRepository;

  public CreateProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product) {
    validateProduct(product);
    return productRepository.save(product);
  }

  private void validateProduct(Product product) {
    validateExistance(product);
    validateData(product);
  }

  private void validateExistance(Product product) {
    if (productRepository.exists(product)) {
      throw new EntityExistsException();
    }
  }

  private void validateData(Product product) {
    // TODO Auto-generated method stub
  }


}

package com.cristian.simplestore.domain.services.product;

import javax.persistence.EntityNotFoundException;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;

public class UpdateProductService {

  private final ProductRepository productRepository;

  public UpdateProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(Product product) {
    Product storedProduct =
        productRepository.find(product).orElseThrow(() -> new EntityNotFoundException());
    storedProduct = copy(product);
    return productRepository.save(storedProduct);
  }

  private Product copy(Product source) {
    validateProduct(source);
    return new Product(source.getId(), source.getName(), source.getDescription(), source.getPrice(),
        source.getPriceSale(), source.isInSale(), source.isActive(), source.getCategory(),
        source.getImages(), source.getStock());
  }

  private void validateProduct(Product product) {

  }

}

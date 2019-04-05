package com.cristian.simplestore.business.services;

import java.util.List;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;

public interface ProductService {

  List<Product> findAll();

  Product findById(Long id);

  Product create(Product product);

  Product create(ProductCreateRequest form);

  Product update(ProductUpdateRequest form);

  void deleteById(Long id);

  public long count();
}

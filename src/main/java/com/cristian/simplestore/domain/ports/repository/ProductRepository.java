package com.cristian.simplestore.domain.ports.repository;

import java.util.List;
import java.util.Optional;
import com.cristian.simplestore.domain.models.Product;

public interface ProductRepository {

  Product save(Product product);

  boolean exists(Product product);

  Optional<Product> find(Product product);

  List<Product> findAll();

  void delete(Product product);

  Product update(Product product);

}

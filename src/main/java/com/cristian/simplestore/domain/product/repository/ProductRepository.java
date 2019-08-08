package com.cristian.simplestore.domain.product.repository;

import java.util.List;
import java.util.Optional;

import com.cristian.simplestore.domain.product.Product;

public interface ProductRepository {
	Optional<Product> find(Product product);

	Optional<Product> findById(Long id);

	List<Product> findAll();

	boolean exists(Product product);

	boolean existsByName(String name);

	Product save(Product product);

	void delete(Product product);

	void deleteById(Long id);

}

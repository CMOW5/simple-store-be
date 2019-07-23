package com.cristian.simplestore.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.persistence.entities.Product;

public interface ProductRepository {

	Iterable<Product> findAll();// extends JpaRepository<Product, Long> {

	Page<Product> findAll(PageRequest of);

	Optional<Product> findById(Long id);

	Product save(Product product);

	void deleteById(Long id);

	long count();
}

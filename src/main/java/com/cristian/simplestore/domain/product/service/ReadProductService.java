package com.cristian.simplestore.domain.product.service;

import java.util.List;
import java.util.Optional;

import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;

public class ReadProductService {

	private final ProductRepository productRepository;

	public ReadProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Optional<Product> execute(Product product) {
		return productRepository.find(product);
	}

	public List<Product> execute() {
		return productRepository.findAll();
	}

	public Optional<Product> execute(Long id) {
		return productRepository.findById(id);
	}

	public Paginated<Product> execute(int page, int size) {
		return productRepository.findAll(page, size);
	}
}

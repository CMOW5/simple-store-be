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
	
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	public Optional<Product> find(Product product) {
		return productRepository.find(product);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Paginated<Product> findAll(int page, int size) {
		return productRepository.findAll(page, size);
	}

	public Paginated<Product> searchByTerm(String searchTerm, int page, int size) {
		return productRepository.searchByTerm(searchTerm, page, size);
	}
}

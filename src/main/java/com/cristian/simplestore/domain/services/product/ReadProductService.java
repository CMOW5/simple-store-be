package com.cristian.simplestore.domain.services.product;

import java.util.List;
import java.util.Optional;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;

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
}

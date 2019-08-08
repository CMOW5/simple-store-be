package com.cristian.simplestore.domain.product.service;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;

public class DeleteProductService {

	ProductRepository productRepository;

	public DeleteProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void execute(Product product) {
		productRepository.delete(product);
	}

	public void execute(Long id) {
		productRepository.deleteById(id);
	}

}

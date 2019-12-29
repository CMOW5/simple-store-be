package com.cristian.simplestore.domain.product.service;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;

public class DeleteProductService {

	ProductRepository productRepository;

	public DeleteProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void delete(Product product) {
		productRepository.delete(product);
	}

	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

}

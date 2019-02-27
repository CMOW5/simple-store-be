package com.cristian.simplestore.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> findAllProducts() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
	
	public Product findById(long id) {
		Optional<Product> product = productRepository.findById(id);
		return product.isPresent() ? product.get() : null;
	}
	
	public Product create(Product product) {
		return productRepository.save(product);
	}
	
	public Product update(long id, Product product) {
		product.setId(id);
		return productRepository.save(product);
	}
	
	public void deleteById(long id) {
		productRepository.deleteById(id);
	}
	
	public Page<Product>  test() {
		Page<Product> products = productRepository.findAll(new PageRequest(0,20));
		return products;
	}
	
	public long count() {
		 return this.productRepository.count();
	}
}

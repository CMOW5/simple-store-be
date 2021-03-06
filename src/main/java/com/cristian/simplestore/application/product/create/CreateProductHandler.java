package com.cristian.simplestore.application.product.create;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.product.ProductFactory;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.service.CreateProductService;

@Component
public class CreateProductHandler {

	private final CreateProductService createProductService;
	private final ProductFactory productFactory;  

	@Autowired
	public CreateProductHandler(CreateProductService createProductService, ProductFactory productFactory) {
		this.createProductService = createProductService;
		this.productFactory = productFactory;
	}

	@Transactional
	public Product create(CreateProductCommand command) {
		Product product = productFactory.create(command);
		return createProductService.create(product);
	}
}
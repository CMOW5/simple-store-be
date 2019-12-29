package com.cristian.simplestore.application.product.update;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.product.ProductFactory;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.service.UpdateProductService;

@Component
public class UpdateProductHandler {

	private final UpdateProductService updateProductService;
	private final ProductFactory productFactory;
	
	@Autowired
	public UpdateProductHandler(UpdateProductService updateProductService,
			ProductFactory productFactory) {
		this.updateProductService = updateProductService;
		this.productFactory = productFactory;
	}

	@Transactional
	public Product update(UpdateProductCommand command) {
		Product product = productFactory.create(command);
		return updateProductService.update(product);
	}
}

package com.cristian.simplestore.application.handler.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.services.product.ReadProductService;

@Component
public class ReadProductHandler {
	
	private final ReadProductService readProductService;
	
	@Autowired
	public ReadProductHandler(ReadProductService readProductService) {
		this.readProductService = readProductService;
	}

	public List<Product> findAll() {
		return readProductService.execute();
	}

	public Optional<Product> findById(Long id) {
		return readProductService.execute(id);
	}

}

package com.cristian.simplestore.application.product.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.service.ReadProductService;

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

	public Paginated<Product> findAll(int page, int size) {
		return readProductService.execute(page, size);			
	}

}

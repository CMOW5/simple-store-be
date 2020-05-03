package com.cristian.simplestore.application.product.read;

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
		return readProductService.findAll();
	}

	public Optional<Product> findById(Long id) {
		return readProductService.findById(id);
	}

	public Paginated<Product> findAll(int page, int size) {
		return readProductService.findAll(page, size);			
	}

	public Paginated<Product> searchByTerm(String searchTerm, int page, int size) {
		if (searchTerm == null || searchTerm.trim().isEmpty()) {
			return readProductService.findAll(page, size);
		}
		return readProductService.searchByTerm(searchTerm, page, size);
	}

}

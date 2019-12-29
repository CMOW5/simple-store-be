package com.cristian.simplestore.application.product.delete;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.product.exceptions.ProductNotFoundException;
import com.cristian.simplestore.domain.product.service.DeleteProductService;

@Component
public class DeleteProductHandler {

	private final DeleteProductService deleteProductService;

	@Autowired
	public DeleteProductHandler(DeleteProductService deleteProductService) {
		this.deleteProductService = deleteProductService;
	}

	@Transactional
	public void deleteById(Long id) {
		try {
			deleteProductService.deleteById(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new ProductNotFoundException(id);
		}	
	}

}
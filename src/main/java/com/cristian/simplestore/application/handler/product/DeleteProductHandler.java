package com.cristian.simplestore.application.handler.product;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.services.product.DeleteProductService;

@Component
public class DeleteProductHandler {

	private final DeleteProductService deleteProductService;

	@Autowired
	public DeleteProductHandler(DeleteProductService deleteProductService) {
		this.deleteProductService = deleteProductService;
	}

	@Transactional
	public void execute(Long id) {
		try {
			deleteProductService.execute(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The product with the given id was not found");
		}	
	}

}

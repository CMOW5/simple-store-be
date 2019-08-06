package com.cristian.simplestore.application.category.handler;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.services.category.DeleteCategoryService;

@Component
public class DeleteCategoryHandler {

	private final DeleteCategoryService deleteCategoryService;
	
	@Autowired
	public DeleteCategoryHandler(DeleteCategoryService deleteCategoryService) {
		this.deleteCategoryService = deleteCategoryService;
	}
	
	@Transactional
	public void execute(Long id) {
		try {
			deleteCategoryService.execute(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The product with the given id was not found");
		}	
	}

}

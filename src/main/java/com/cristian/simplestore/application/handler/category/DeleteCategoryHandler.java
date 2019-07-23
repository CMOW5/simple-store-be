package com.cristian.simplestore.application.handler.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.services.category.DeleteCategoryService;

@Component
public class DeleteCategoryHandler {

	private final DeleteCategoryService deleteCategoryService;
	
	@Autowired
	public DeleteCategoryHandler(DeleteCategoryService deleteCategoryService) {
		this.deleteCategoryService = deleteCategoryService;
	}
	
	public void execute(Long id) {
		deleteCategoryService.execute(id);
	}

}

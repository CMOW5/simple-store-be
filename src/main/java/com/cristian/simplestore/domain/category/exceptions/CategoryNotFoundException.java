package com.cristian.simplestore.domain.category.exceptions;

import javax.persistence.EntityNotFoundException;

public final class CategoryNotFoundException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2323038983027877004L;
	
	private static final String MESSAGE = "The category with id = %d does not exists";

	public CategoryNotFoundException(Long id) {
		super(String.format(MESSAGE, id));
	}
}

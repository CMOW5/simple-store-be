package com.cristian.simplestore.domain.product.exceptions;

import javax.persistence.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 231400538033235880L;

	private static final String MESSAGE = "The product with the id = %d not found";
	
	public ProductNotFoundException(Long id) {
		super(String.format(MESSAGE, id));
	}
}

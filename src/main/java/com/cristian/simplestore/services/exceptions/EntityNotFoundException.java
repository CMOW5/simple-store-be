package com.cristian.simplestore.services.exceptions;

public class EntityNotFoundException extends RuntimeException  {
		
	private static final long serialVersionUID = 4847767458103151207L;

	public EntityNotFoundException() {
        super("Entity Not Found");
    }
	
	public EntityNotFoundException(String message) {
        super(message);
    }
	
	public EntityNotFoundException(Throwable err) {
		super(err);
    }
	
	public EntityNotFoundException(String errorMessage, Throwable err) {
		super(errorMessage, err);
    }
}

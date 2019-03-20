package com.cristian.simplestore.web.utils.response;

import org.springframework.validation.FieldError;

public class ApiError {
	
	private String field;
	private String defaultMessage;
	private Object rejectedValue;
	
	public ApiError() {}
	
	public ApiError(FieldError fieldError) {
		this.field = fieldError.getField();
		this.defaultMessage = fieldError.getDefaultMessage();
		this.rejectedValue = fieldError.getRejectedValue();
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getDefaultMessage() {
		return defaultMessage;
	}
	
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	
	public Object getRejectedValue() {
		return rejectedValue;
	}
	
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
	
}

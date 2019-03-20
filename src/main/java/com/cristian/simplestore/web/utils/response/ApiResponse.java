package com.cristian.simplestore.web.utils.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

public class ApiResponse {
	
	private static final String CONTENT_KEY = "content";
	private static final String ERRORS_KEY = "errors";
	private static final String STATUS_KEY = "status";
	
	private Map<String, Object> attachments;
	
	private Object content;
	
	private HttpStatus status = HttpStatus.OK;
	
	public ApiResponse() {
		attachments = new HashMap<String, Object>();
		this.attachments.put(CONTENT_KEY, null);
	}
		
	public ApiResponse status(HttpStatus status) {
		this.status = status;
		this.attachments.put(STATUS_KEY, this.status.value());
		return this;
	}
	
	public <T> ApiResponse content(T content) {
		this.content = content;
		this.attachments.put(CONTENT_KEY, this.content);
		return this;
	}
	
	public ApiResponse errors(BindException exception) {
		List<ApiError> errors = new ArrayList<>();
		
		for (FieldError fieldError : exception.getFieldErrors()) {
			ApiError error = new ApiError(fieldError);
			errors.add(error);
		}
		
		this.attachments.put(ERRORS_KEY, errors);
		return this;
	}
	
	
	public <T> ApiResponse attach(String key, T attachment) {
		this.attachments.put(key, attachment);
		return this;
	}
	
	public ResponseEntity<Map<String, Object>> build() {		
		return new ResponseEntity<>(attachments, status);
	}
}

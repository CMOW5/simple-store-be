package com.cristian.simplestore.utils.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse<T> {
	
	private static final String CONTENT_KEY = "content";
	
	private Map<String, T> attachments;
	
	private T content;
	
	private HttpStatus status = HttpStatus.OK;
	
	public CustomResponse() {
		attachments = new HashMap<String, T>();
		this.attachments.put(CONTENT_KEY, null);
	}
		
	public CustomResponse<T> status(HttpStatus status) {
		this.status = status;
		return this;
	}
	
	public CustomResponse<T> content(T content) {
		this.content = content;
		this.attachments.put(CONTENT_KEY, this.content);
		return this;
	}
	
	public CustomResponse<T> attach(String key, T attachment) {
		this.attachments.put(key, attachment);
		return this;
	}
	
	public ResponseEntity<Map<String, T>> build() {		
		return new ResponseEntity<>(attachments, status);
	}
}

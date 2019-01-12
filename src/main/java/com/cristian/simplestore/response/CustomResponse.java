package com.cristian.simplestore.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomResponse {
	
	private static final String CONTENT_KEY = "content";
	
	private Map<String, Object> attachments;
	
	public CustomResponse() {
		attachments = new HashMap<String, Object>();
		this.attachments.put(CONTENT_KEY, null);
	}
	
	public CustomResponse attachContent(Object content) {
		this.attachments.put(CONTENT_KEY, content);
		return this;
	}
	
	public CustomResponse attach(String key, Object value) {
		this.attachments.put(key, value);
		return this;
	}
	
	public Map<String, Object> build() {
		// ObjectMapper mapper = new ObjectMapper();
		
		return this.attachments;
	}
}

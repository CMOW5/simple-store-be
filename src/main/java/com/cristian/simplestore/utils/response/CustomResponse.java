package com.cristian.simplestore.utils.response;

import java.util.HashMap;
import java.util.Map;

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
		return this.attachments;
	}
}

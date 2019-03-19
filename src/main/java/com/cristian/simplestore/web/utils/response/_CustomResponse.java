package com.cristian.simplestore.web.utils.response;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class _CustomResponse {
	
	private static final String CONTENT_KEY = "content";
	
	private Map<String, Object> attachments;
	
	public _CustomResponse() {
		attachments = new HashMap<String, Object>();
		this.attachments.put(CONTENT_KEY, null);
	}
	
	public _CustomResponse attachContent(Object content) {
		this.attachments.put(CONTENT_KEY, content);
		return this;
	}
	
	public _CustomResponse attach(String key, Object value) {
		this.attachments.put(key, value);
		return this;
	}
	
	public Map<String, Object> build() {		
		return this.attachments;
	}
}

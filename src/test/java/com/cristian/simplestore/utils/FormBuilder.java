package com.cristian.simplestore.utils;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FormBuilder {
	
	MultiValueMap<String, Object> form;
	
	public FormBuilder() {
		form = new LinkedMultiValueMap<>();
	}
	
	public <T> FormBuilder add(String key, T value) {
		if (value instanceof List) {
			form.addAll(key, (List<?>) value);
		} else {
			form.set(key, value);
		}
		return this;
	}
	
	public Object get(String key) {
		return form.getFirst(key);
	}
	
	public List<Object> getAll(String key) {
		return form.get(key);
	}
	
	public MultiValueMap<String, Object> build() {
		return this.form;
	}

	
	
}

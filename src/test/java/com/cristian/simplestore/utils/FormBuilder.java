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
			form.addAll(key, (List<? extends Object>) value);
		} else {
			form.add(key, value);
		}
		return this;
	}
	
	public MultiValueMap<String, Object> build() {
		return this.form;
	}
	
}

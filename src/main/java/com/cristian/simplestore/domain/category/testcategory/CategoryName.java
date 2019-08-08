package com.cristian.simplestore.domain.category.testcategory;

public class CategoryName {
	
	private static final String INVALID_NAME_EXCEPTION = "the given name %s is invalid";
	
	private final String value;
	
	public CategoryName(String name) {
		guardValidName(name);
		value = name;
	}

	private void guardValidName(String name) {
		if (name.length() < 3) {
			throw new IllegalArgumentException(String.format(INVALID_NAME_EXCEPTION, name));
		}
	}
	
	public String getValue() {
		return value;
	}
}

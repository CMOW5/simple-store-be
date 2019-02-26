package com.cristian.simplestore.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cristian.simplestore.form.CategoryCreateForm;

public class CategoryCreateValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CategoryCreateForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        CategoryCreateForm categoryForm = (CategoryCreateForm) target;
//        if (categoryForm.getName() < 0) {
//        	errors.rejectValue("age", "negativevalue");
//        } 
//        
//        if (categoryForm.getParentCategory()) {
//        	errors.rejectValue("age", "too.darn.old");
//        }
        errors.rejectValue("name", "too.darn.old");
	}

}

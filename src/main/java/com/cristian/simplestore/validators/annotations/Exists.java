package com.cristian.simplestore.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {
    
	String message() default "the field is not unique";
    
	/**
	 * @return size the table in which the attribute must be unique
	 */
	String table() default "";

	/**
	 * @return the column to compare to
	 */
	String column() default "";
	
	long exceptId();
	
	Class<?>[] groups() default {};
    
	Class<? extends Payload>[] payload() default {};
}

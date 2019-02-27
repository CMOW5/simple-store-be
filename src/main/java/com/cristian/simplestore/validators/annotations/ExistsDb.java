package com.cristian.simplestore.validators.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cristian.simplestore.validators.ExistsDbValidator;

@Constraint(validatedBy = ExistsDbValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsDb {
 
    String message() default "the field already exists";
 
    String table();
    
    String columnName();
    
    String columnValueField();
    
    String exceptIdColumn() default "";
      
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; 
 
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	ExistsDb[] value();
    }
}

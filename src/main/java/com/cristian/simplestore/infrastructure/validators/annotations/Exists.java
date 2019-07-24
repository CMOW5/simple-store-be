package com.cristian.simplestore.infrastructure.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.cristian.simplestore.infrastructure.validators.ExistsValidator;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

  String message() default "the thing doesn't exists";

  /**
   * @return size the table in which the attribute must be unique
   */
  String table();

  /**
   * @return the column to compare to
   */
  String column();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

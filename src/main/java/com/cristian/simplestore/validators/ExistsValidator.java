package com.cristian.simplestore.validators;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<Exists, String> {

  private String table;
  
  private String column;
  
  // private Long exceptId;
  
  @PersistenceContext
  EntityManager entityManager;
	
  @Override
  public void initialize(Exists name) {
	  this.table = name.table();
	  this.column = name.column();
	  // this.exceptId = name.exceptId().;
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext cxt) {
	  String query = "SELECT " + column + " FROM " + table + " WHERE " + column + "= ?";
	  Query q = entityManager.createNativeQuery(query);
	  q.setParameter(1, name);
	  
	  String result;
	  
	  try {
	  	result = (String) q.getSingleResult();
	  	// List<Object[]> cats = q.getResultList();
	  } catch (NoResultException e) {
		  return true;
	  }
	  	
	  if (name.compareToIgnoreCase(result) == 0) {
		  return false;
	  } else {
		  return true;
	  }
  }

}


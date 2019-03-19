package com.cristian.simplestore.web.validators;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cristian.simplestore.web.validators.annotations.Exists;

public class ExistsValidator implements ConstraintValidator<Exists, Object> {

	private String table;

	private String column;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void initialize(Exists name) {
		this.table = name.table();
		this.column = name.column();
	}

	@Override
	public boolean isValid(Object columnValue, ConstraintValidatorContext cxt) {
		String strQuery = "SELECT " + column + " FROM " + table + " WHERE " + column + "= ?";
		Query query = entityManager.createNativeQuery(strQuery);
		query.setParameter(1, columnValue);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}

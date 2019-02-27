package com.cristian.simplestore.validators;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.cristian.simplestore.validators.annotations.ExistsDb;

public class ExistsDbValidator implements ConstraintValidator<ExistsDb, Object> {

	private String table;
	private String columnName;
	private String columnValueField;
	private String exceptIdColumn;

	
	@PersistenceContext
	EntityManager entityManager;

	public void initialize(ExistsDb constraintAnnotation) {
		this.table = constraintAnnotation.table();
		this.columnName = constraintAnnotation.columnName();
		this.columnValueField = constraintAnnotation.columnValueField();
		this.exceptIdColumn = constraintAnnotation.exceptIdColumn();
	}

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		String uniqueFieldValue = (String) new BeanWrapperImpl(value).getPropertyValue(columnValueField);

		if (exceptIdColumn.isEmpty()) {
			return !entryExists(table, columnName, uniqueFieldValue);
		} else {
			Object exceptIdValue = new BeanWrapperImpl(value).getPropertyValue(exceptIdColumn);
			return !entryExistsExceptWithId(table, columnName, uniqueFieldValue, exceptIdColumn, exceptIdValue);
		}
	}
	
	/**
	 * verify if a record has the given column value for the given column name in a the given table
	 * @param table the table name 
	 * @param columnName the table column
	 * @param columnValue the column value
	 * @return true if the entry exists
	 */
	private boolean entryExists(String table, String columnName, String columnValue) {
		String query = "SELECT " + columnName + " FROM " + table + " WHERE " + columnName + "= ?";
		Query q = entityManager.createNativeQuery(query);
		q.setParameter(1, columnValue);
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	/**
	 * verify if a record has the given column value for the given column name in a the given table, if the 
	 * given record has the given id in the id column name, the verification is excluded
	 * @param table
	 * @param columnName
	 * @param columnValue
	 * @param idColumnName
	 * @param idColumnValue
	 * @return true if the entry exists
	 */
	private boolean entryExistsExceptWithId(String table, String columnName, String columnValue, String idColumnName, Object idColumnValue) {
		String query = "SELECT " + columnName + 
				" FROM " + table + " WHERE " + columnName + "= ?" + " AND " + idColumnName + " != ?";
		Query q = entityManager.createNativeQuery(query);
		q.setParameter(1, columnValue);
		q.setParameter(2, idColumnValue);
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}

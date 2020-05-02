package com.cristian.simplestore.infrastructure.database.fulltextsearch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FullTextEntityManagerBean {
	
	private final EntityManager entityManager;
	
	@Autowired
	public FullTextEntityManagerBean(final EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	
	@Bean
	public FullTextEntityManager fullTextEntityManager() {
		return Search.getFullTextEntityManager(entityManager);
	}

}

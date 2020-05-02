package com.cristian.simplestore.infrastructure.database.fulltextsearch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class used to we have to trigger Lucene to build the index initially 
 * that are already in the database
 * 
 * @author bit5
 */
@Component
public class IndexInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(IndexInitializer.class);
	
	private final EntityManager entityManager;
	
	@Autowired
	public IndexInitializer(final EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

}

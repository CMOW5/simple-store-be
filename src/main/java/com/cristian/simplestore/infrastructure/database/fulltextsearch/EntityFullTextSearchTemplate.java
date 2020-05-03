package com.cristian.simplestore.infrastructure.database.fulltextsearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class EntityFullTextSearchTemplate<T> {

	private final FullTextEntityManager fullTextEntityManager;

	private final Class<T> entityType;

	public EntityFullTextSearchTemplate(FullTextEntityManager fullTextEntityManager, Class<T> entityType) {
		this.fullTextEntityManager = fullTextEntityManager;
		this.entityType = entityType;
	}

	public Page<T> searchByName(String searchTerm, int page, int size) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(entityType)
				.get();

		List<String> parts = Arrays.asList(searchTerm.split(" "));

		List<String> formattedParts = parts.stream().map(part -> String.format("(%s* | %s~3)", part, part))
				.collect(Collectors.toList());

		String queryString = String.join(" ", formattedParts);

		org.apache.lucene.search.Query query = queryBuilder.simpleQueryString()
				.onFields(mainField(), secondaryFields().stream().toArray(String[]::new)).matching(queryString)
				.createQuery();

		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, entityType);

		// in order to build the paginator we need the total number
		// of "hits" for this query, since I couln't find the way to create
		// a count query then this hack is needed
		long totalElements = persistenceQuery.getResultList().size();

		persistenceQuery.setFirstResult(page * size); // start from the (page * size)th element
		persistenceQuery.setMaxResults(size); // return (size) elements

		// it is safe to do this supression warning here
		// unfortunately FullTextEntityManager does not support
		// TypedQuery's
		@SuppressWarnings("unchecked")
		List<T> entities = persistenceQuery.getResultList();
		Pageable pageable = PageRequest.of(page, size);

		return new PageImpl<>(entities, pageable, totalElements);
	}

	public abstract String mainField();

	public List<String> secondaryFields() {
		return Collections.emptyList();
	}
}

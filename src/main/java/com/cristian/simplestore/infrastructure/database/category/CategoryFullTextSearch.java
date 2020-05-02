package com.cristian.simplestore.infrastructure.database.category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryFullTextSearch {

	private final FullTextEntityManager fullTextEntityManager;

	@Autowired
	public CategoryFullTextSearch(FullTextEntityManager fullTextEntityManager) {
		this.fullTextEntityManager = fullTextEntityManager;
	}

	public Page<CategoryEntity> searchByName(String searchTerm, int page, int size) {
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(CategoryEntity.class).get();

		List<String> parts = Arrays.asList(searchTerm.split(" "));

		List<String> formattedParts = parts.stream().map(part -> String.format("(%s* | %s~3)", part, part))
				.collect(Collectors.toList());

		String queryString = String.join(" ", formattedParts);

		org.apache.lucene.search.Query query = queryBuilder.simpleQueryString().onField("name").matching(queryString)
				.createQuery();
		
		javax.persistence.Query persistenceQuery =
			    fullTextEntityManager.createFullTextQuery(query, CategoryEntity.class);
		
		// in order to build the paginator we need the total number
		// of "hits" for this query, since I couln't find the way to create
		// a count query then this hack is needed
		long totalElements = persistenceQuery.getResultList().size();
		
		persistenceQuery.setFirstResult(page * size); //start from the (page * size)th element
		persistenceQuery.setMaxResults(size); //return (size) elements
		
	
		// it is safe to do this supression warning here
		// unfortunately FullTextEntityManager does not support
		// TypedQuery's
		@SuppressWarnings("unchecked") 
		List<CategoryEntity> categories = persistenceQuery.getResultList();
		Pageable pageable = PageRequest.of(page, size);
		
		return new PageImpl<>(categories , pageable, totalElements);
	}

}

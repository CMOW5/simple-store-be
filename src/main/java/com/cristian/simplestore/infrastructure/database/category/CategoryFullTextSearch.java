package com.cristian.simplestore.infrastructure.database.category;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristian.simplestore.infrastructure.database.fulltextsearch.EntityFullTextSearchTemplate;

@Service
public class CategoryFullTextSearch extends EntityFullTextSearchTemplate<CategoryEntity> {

	@Autowired
	public CategoryFullTextSearch(FullTextEntityManager fullTextEntityManager) {
		super(fullTextEntityManager, CategoryEntity.class);
	}

	@Override
	public String mainField() {
		return "name";
	}
}

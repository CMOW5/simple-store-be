package com.cristian.simplestore.infrastructure.database.product;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristian.simplestore.infrastructure.database.fulltextsearch.EntityFullTextSearchTemplate;

@Service
public class ProductFullTextSearch extends EntityFullTextSearchTemplate<ProductEntity> {
	
	@Autowired
	public ProductFullTextSearch(FullTextEntityManager fullTextEntityManager) {
		super(fullTextEntityManager, ProductEntity.class);
	}

	@Override
	public String mainField() {
		return "name";
	}

}

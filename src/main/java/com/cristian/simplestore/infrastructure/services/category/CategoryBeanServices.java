package com.cristian.simplestore.infrastructure.services.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.category.services.CreateCategoryService;
import com.cristian.simplestore.domain.category.services.DeleteCategoryService;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.category.services.UpdateCategoryService;
import com.cristian.simplestore.infrastructure.database.category.CategoryRepositoryJpa;

@Configuration
public class CategoryBeanServices {
	
	@Bean
	public CreateCategoryService createCategoryService(CategoryRepositoryJpa jpaRepo) {
		return new CreateCategoryService(jpaRepo);
	}

	@Bean
	public ReadCategoryService readCategoryService(CategoryRepositoryJpa jpaRepo) {
		return new ReadCategoryService(jpaRepo);
	}

	@Bean
	public UpdateCategoryService updateCategoryService(CategoryRepositoryJpa jpaRepo) {
		return new UpdateCategoryService(jpaRepo);
	}
	
	@Bean
	public DeleteCategoryService deleteCategoryService(CategoryRepositoryJpa jpaRepo) {
		return new DeleteCategoryService(jpaRepo);
	}
}

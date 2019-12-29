package com.cristian.simplestore.infrastructure.services.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.category.services.CreateCategoryService;
import com.cristian.simplestore.domain.category.services.DeleteCategoryService;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.category.services.UpdateCategoryService;
import com.cristian.simplestore.infrastructure.database.category.CategoryRepositoryJpa;
import com.cristian.simplestore.infrastructure.database.category.CategoryRepositorySpringJpa;

@Configuration
public class CategoryBeanServices {
	
	@Bean
	public CreateCategoryService createCategoryService(CategoryRepositorySpringJpa jpaRepo) {
		return new CreateCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}

	@Bean
	public ReadCategoryService readCategoryService(CategoryRepositorySpringJpa jpaRepo) {
		return new ReadCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}

	@Bean
	public UpdateCategoryService updateCategoryService(CategoryRepositorySpringJpa jpaRepo) {
		return new UpdateCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}
	
	@Bean
	public DeleteCategoryService deleteCategoryService(CategoryRepositorySpringJpa jpaRepo) {
		return new DeleteCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}
}

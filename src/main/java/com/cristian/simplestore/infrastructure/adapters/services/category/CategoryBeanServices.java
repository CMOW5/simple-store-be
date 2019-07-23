package com.cristian.simplestore.infrastructure.adapters.services.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.services.category.CreateCategoryService;
import com.cristian.simplestore.domain.services.category.DeleteCategoryService;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.category.UpdateCategoryService;
import com.cristian.simplestore.infrastructure.adapters.repository.category.CategoryRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.category.CategoryRepositoryJpaInterface;

@Configuration
public class CategoryBeanServices {

	@Bean
	public CreateCategoryService createCategoryService(CategoryRepositoryJpaInterface jpaRepo) {
		return new CreateCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}

	@Bean
	public ReadCategoryService readCategoryService(CategoryRepositoryJpaInterface jpaRepo) {
		return new ReadCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}

	@Bean
	public UpdateCategoryService updateCategoryService(CategoryRepositoryJpaInterface jpaRepo) {
		return new UpdateCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}
	
	@Bean
	public DeleteCategoryService deleteCategoryService(CategoryRepositoryJpaInterface jpaRepo) {
		return new DeleteCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}
}

package com.cristian.simplestore.infrastructure.adapters.services.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.services.category.CreateCategoryService;
import com.cristian.simplestore.domain.services.category.DeleteCategoryService;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.category.UpdateCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.infrastructure.adapters.repository.category.CategoryRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.category.CategoryRepositorySpringJpa;

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
	public UpdateCategoryService updateCategoryService(CategoryRepositorySpringJpa jpaRepo, CreateImageService createImageService) {
		return new UpdateCategoryService(new CategoryRepositoryJpa(jpaRepo), createImageService);
	}
	
	@Bean
	public DeleteCategoryService deleteCategoryService(CategoryRepositorySpringJpa jpaRepo) {
		return new DeleteCategoryService(new CategoryRepositoryJpa(jpaRepo));
	}
}

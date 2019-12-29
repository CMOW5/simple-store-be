package com.cristian.simplestore.integration.domain.category;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cristian.simplestore.basetest.BaseIntegrationTest;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.CreateCategoryService;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.unit.domain.category.databuilder.CategoryTestDataBuilder;
import com.cristian.simplestore.utils.category.CategoryGenerator;

public class CreateCategoryServiceTest extends BaseIntegrationTest {
	
	@Autowired
	private ReadCategoryService readCategoryService;
	
	@Autowired
	private CreateCategoryService createCategoryService;
	
	@Autowired
    private CategoryGenerator categoryGenerator;
	
		
	@Test
	public void testItSavesTheCategoryOnDb() {
		// arrange
		Category parent = categoryGenerator.saveRandomCategoryOnDb();
		Category category = new CategoryTestDataBuilder().parent(parent).build();
		
		Category createdCategory = createCategoryService.create(category);
		
		// assert
		Category storedCategory = findById(createdCategory.getId());
		
		assertThat(storedCategory.getName()).isEqualTo(category.getName());
		assertThat(storedCategory.getParent().getName()).isEqualTo(parent.getName());
	}
	
	private Category findById(Long id) {
		return readCategoryService.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
}

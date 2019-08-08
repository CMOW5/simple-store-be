package com.cristian.simplestore.domain.integration.category;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.category.services.UpdateCategoryService;
import com.cristian.simplestore.infrastructure.controllers.BaseIntegrationTest;
import com.cristian.simplestore.utils.category.CategoryGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateCategoryServiceTest extends BaseIntegrationTest {
	
	@Autowired
    private CategoryGenerator categoryGenerator;
	
	@Autowired
	private UpdateCategoryService updateCategoryService;
	
	@Autowired
	private ReadCategoryService readCategoryService;
	
	@Test
	public void testItCorrectlyUpdatesTheParentCategory() {
		// arrange
		Category categoryA = categoryGenerator.new Builder().name("category A").save();
		Category categoryB = categoryGenerator.new Builder().name("category B").parent(categoryA).save();
		Category categoryC = categoryGenerator.new Builder().name("category C").parent(categoryB).save();
		Category categoryD = categoryGenerator.new Builder().name("category D").parent(categoryA).save();
		
		Long newParenCategoryId = categoryB.getId();
		
		// act		
		Category updatedCategoryA = updateCategoryService.execute(categoryA.getId(), categoryA.getName(), newParenCategoryId, null);

		// assert
		categoryB = findById(categoryB.getId());
		categoryC = findById(categoryC.getId());
		categoryD = findById(categoryD.getId());
		assertThat(updatedCategoryA.getParent().getId()).isEqualTo(categoryB.getId());
		assertThat(categoryB.getParent()).isNull();
		assertThat(categoryC.getParent().getId()).isEqualTo(categoryB.getId());
		assertThat(categoryD.getParent().getId()).isEqualTo(categoryA.getId());
	}
	
	@Test
	public void testItCorrectlyUpdatesTheParentCategoryDeep() {
		// arrange
		Category categoryA = categoryGenerator.new Builder().name("category A").save();
		Category categoryB = categoryGenerator.new Builder().name("category B").parent(categoryA).save();
		Category categoryC = categoryGenerator.new Builder().name("category C").parent(categoryB).save();
		Category categoryD = categoryGenerator.new Builder().name("category D").parent(categoryA).save();
		
		Long newParenCategoryId = categoryA.getId();
		
		// act		
		Category updatedCategoryC = updateCategoryService.execute(categoryC.getId(), categoryC.getName(), newParenCategoryId, null);

		// assert
		categoryA = findById(categoryA.getId());
		categoryB = findById(categoryB.getId());
		categoryD = findById(categoryD.getId());
		assertThat(categoryA.getParent()).isNull();
		assertThat(updatedCategoryC.getParent().getId()).isEqualTo(categoryA.getId());
		assertThat(categoryB.getParent().getId()).isEqualTo(categoryA.getId());
		assertThat(categoryD.getParent().getId()).isEqualTo(categoryA.getId());
	}
	
	private Category findById(Long id) {
		return readCategoryService.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
}

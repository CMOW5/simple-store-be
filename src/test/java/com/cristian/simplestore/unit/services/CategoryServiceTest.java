package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.CategoryService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.persistence.respositories.ImageRepository;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest extends BaseTest {
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired
	CategoryTestsUtils utils;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ImageRepository imageRepository;
	
	@Before
	public void setUp() {
		cleanUpDb();
	}
	
	@After
    public void tearDown() {
		cleanUpDb();
    }
	
	public void cleanUpDb() {
		categoryRepository.deleteAll();
		imageRepository.deleteAll();
	}
	
	@Test
	public void testItfindsAllCategories() {
		long CATEGORIES_SIZE = 4;
		utils.saveRandomCategoriesOnDB(CATEGORIES_SIZE);
		
		List<Category> foundCategories = this.categoryService.findAll();
		
		assertThat(foundCategories.size()).isEqualTo(CATEGORIES_SIZE);
	}
	 
	@Test
	public void testItfindsCategoryById() {
		Category category = utils.saveRandomCategoryOnDB();
		
		Category foundCategory = this.categoryService.findCategoryById(category.getId());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void testItcreatesACategoryWithForm() {
		CategoryCreateForm form = utils.generateRandomCategoryCreateForm();
		
		Category savedCategory = this.categoryService.create(form);
		
		assertThat(savedCategory.getName()).isEqualTo(form.getName());
		assertThat(savedCategory.getParentCategory()).isEqualTo(form.getParentCategory());
		assertThat(savedCategory.getImage()).isNotNull();

	}
	
	@Test 
	public void testItupdatesACategoryWithForm() {
		Category categoryToUpdate = utils.saveRandomCategoryOnDB();
		CategoryUpdateForm newCategoryData = utils.generateRandomCategoryUpdateForm();
		newCategoryData.setId(categoryToUpdate.getId());
		
		Category updatedCategory = this.categoryService.update(newCategoryData);
		
		assertThat(updatedCategory.getId()).isEqualTo(newCategoryData.getId());
		assertThat(updatedCategory.getName()).isEqualTo(newCategoryData.getName());
		assertThat(updatedCategory.getParentCategory().getId()).isEqualTo(newCategoryData.getParentCategory().getId());
		assertThat(updatedCategory.getImage()).isNotNull();

	}
	
	@Test
	public void testItCorrectlyUpdatesTheParentCategory() {
		Category categoryA = utils.saveRandomCategoryOnDB();
		Category categoryB = utils.saveRandomCategoryOnDB();
				
		CategoryUpdateForm categoryBform = utils.generateRandomCategoryUpdateForm();
		categoryBform.setId(categoryB.getId());
		
		// here we manually create a circular reference
		// Between the category A and its parent, the category B.
		// example B -> A -> B is not allowed
		// it should update to null -> A -> B
		categoryA.setParentCategory(categoryB);
		categoryA = this.categoryRepository.save(categoryA);
		categoryBform.setParentCategory(categoryA);
		
		Category updatedBCategory = this.categoryService.update(categoryBform);
		
		categoryA = this.categoryRepository.findById(categoryA.getId()).get();
		
		assertThat(categoryA.getParentCategory()).isNull();
		assertThat(updatedBCategory.getParentCategory().getId()).isEqualTo(categoryA.getId());
	}
	
	
	@Test 
	public void testItDeletesACategoryImage() {
		Category categoryToUpdate = utils.saveRandomCategoryOnDB();
		CategoryUpdateForm newCategoryData = utils.generateRandomCategoryUpdateForm();
		newCategoryData.setId(categoryToUpdate.getId());
		newCategoryData.setImageIdToDelete(categoryToUpdate.getImage().getId());
		newCategoryData.setNewImage(null);
		
		Category updatedCategory = this.categoryService.update(newCategoryData);
		
		assertThat(updatedCategory.getImage()).isNull();
	}
	 
	@Test(expected = EntityNotFoundException.class)
	public void delete() {
		Category categoryToDelete = utils.saveRandomCategoryOnDB();
		
		this.categoryService.deleteById(categoryToDelete.getId());
		this.categoryService.findCategoryById(categoryToDelete.getId());
	}
	

}

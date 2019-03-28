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
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest extends BaseTest {
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
		
	@Autowired
	CategoryTestsUtils categoryUtils;
	
	@Autowired
	DbCleaner dbCleaner;
	
	@Before
	public void setUp() {
		cleanUpDb();
	}
	
	@After
    public void tearDown() {
		cleanUpDb();
    }
	
	public void cleanUpDb() {
		dbCleaner.cleanAllTables();
	}
	
	@Test
	public void testItfindsAllCategories() {
		long CATEGORIES_SIZE = 4;
		List<Category> categories = categoryUtils.saveRandomCategoriesOnDB(CATEGORIES_SIZE);
		
		List<Category> foundCategories = categoryService.findAll();
		
		assertThat(foundCategories.size()).isEqualTo(categories.size());
	}
	 
	@Test
	public void testItfindsCategoryById() {
		Category category = categoryUtils.saveRandomCategoryOnDB();
		
		Category foundCategory = categoryService.findById(category.getId());
		
		assertThatTwoCategoriesAreEqual(foundCategory, category);
	}
	
	@Test
	public void testItcreatesACategoryWithForm() {
		CategoryCreateForm form = categoryUtils.generateRandomCategoryCreateForm();
		
		Category createdCategory = categoryService.create(form);
		
		assertThatTwoCategoriesAreEqual(createdCategory, form.getModel());
		assertThat(createdCategory.getImage()).isNotNull();
	}
	
	@Test 
	public void testItupdatesACategoryWithForm() {
		Category categoryToUpdate = categoryUtils.saveRandomCategoryOnDB();
		CategoryUpdateForm newCategoryDataForm = 
				categoryUtils.generateRandomCategoryUpdateForm(categoryToUpdate.getId());
		
		Category updatedCategory = categoryService.update(newCategoryDataForm);
		
		assertThat(updatedCategory.getId()).isEqualTo(newCategoryDataForm.getId());
		assertThatTwoCategoriesAreEqual(updatedCategory, newCategoryDataForm.getModel());
		assertThat(updatedCategory.getImage()).isNotNull();
	}
	
	@Test
	public void testItCorrectlyUpdatesTheParentCategory() {
		Category categoryA = categoryUtils.saveRandomCategoryOnDB();
		Category categoryB = categoryUtils.saveRandomCategoryOnDB();
				
		CategoryUpdateForm categoryBform = 
				categoryUtils.generateRandomCategoryUpdateForm(categoryB.getId());
		
		// here we manually create a circular reference
		// Between the category A and its parent, the category B.
		// example B -> A -> B is not allowed
		// it should update to null -> A -> B
		categoryA.setParentCategory(categoryB);
		categoryA = categoryRepository.save(categoryA);
		categoryBform.setParentCategory(categoryA);
		
		Category expectedCategoryB = categoryService.update(categoryBform);
		Category expectedCategoryA = categoryService.findById(categoryA.getId());
		
		assertThat(expectedCategoryA.getParentCategory()).isNull();
		assertThat(expectedCategoryB.getParentCategory().getId()).isEqualTo(categoryA.getId());
	}
	
	
	@Test 
	public void testItDeletesACategoryImage() {
		Category categoryToUpdate = categoryUtils.saveRandomCategoryOnDB();
		CategoryUpdateForm newCategoryData = 
				categoryUtils.generateRandomCategoryUpdateForm(categoryToUpdate.getId());
		newCategoryData.setImageIdToDelete(categoryToUpdate.getImage().getId());
		newCategoryData.setNewImage(null);
		
		Category updatedCategory = categoryService.update(newCategoryData);
		
		assertThat(updatedCategory.getImage()).isNull();
	}
	 
	@Test(expected = EntityNotFoundException.class)
	public void delete() {
		Category categoryToDelete = categoryUtils.saveRandomCategoryOnDB();
		
		categoryService.deleteById(categoryToDelete.getId());
		
		// this should throw an exception
		categoryService.findById(categoryToDelete.getId());
	}
	
	private void assertThatTwoCategoriesAreEqual(Category c1, Category c2) {
		assertThat(c1.getName()).isEqualTo(c2.getName());
		assertThat(c1.getParentCategory()).isEqualTo(c2.getParentCategory());
	}
}

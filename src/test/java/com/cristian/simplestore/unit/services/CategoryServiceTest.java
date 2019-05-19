package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.CategoryService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.category.CategoryRequestUtils;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest extends BaseTest {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryRequestUtils categoryRequestUtils;

	@Autowired
	CategoryGenerator categoryGenerator;

	@Test
	public void testItfindsAllCategories() {
		int CATEGORIES_SIZE = 4;
		List<Category> categories = categoryGenerator.saveRandomCategoriesOnDb(CATEGORIES_SIZE);

		List<Category> foundCategories = categoryService.findAll();

		assertThat(foundCategories.size()).isEqualTo(categories.size());
	}

	@Test
	public void testItfindACategoryById() {
		Category category = categoryGenerator.saveRandomCategoryOnDb();

		Category foundCategory = categoryService.findById(category.getId());

		assertThatTwoCategoriesAreEqual(foundCategory, category);
	}

	@Test
	public void testItcreatesACategoryWithForm() {
		CategoryCreateRequest categoryCreateData = categoryRequestUtils.new CategoryCreateRequestBuilder().withRandomName()
				.withRandomImage().withRandomParent().build();

		Category createdCategory = categoryService.create(categoryCreateData);

		assertThatTwoCategoriesAreEqual(createdCategory, categoryCreateData.getModel());
		assertThat(createdCategory.getImage()).isNotNull();
	}

	@Test
	public void testItupdatesACategoryWithForm() {
		Category categoryToUpdate = categoryGenerator.saveRandomCategoryOnDb();
		Long originalImageId = categoryToUpdate.getImage().getId();

		CategoryUpdateRequest categoryUpdateData = categoryRequestUtils.new CategoryUpdateRequestBuilder(
				categoryToUpdate.getId()).withRandomName().withRandomImage().withRandomParent()
						.imageIdToDelete(originalImageId).build();

		Category updatedCategory = categoryService.update(categoryUpdateData);

		assertThatTwoCategoriesAreEqual(updatedCategory, categoryUpdateData.getModel());
		assertThat(updatedCategory.getId()).isEqualTo(categoryUpdateData.getId());
		assertThat(updatedCategory.getImage()).isNotNull();
		assertThat(updatedCategory.getImage().getId()).isNotEqualTo(originalImageId);
	}

	@Test
	public void testItCorrectlyUpdatesTheParentCategory() {
		// B -> A
		Category categoryA = categoryGenerator.new Builder().randomName().randomParent().save();
		Category categoryB = categoryA.getParentCategory();
		
		// we attempt to create a circular reference by setting A as parent of B
		// example B -> A -> B is not allowed
		// it should update to null -> A -> B
		CategoryUpdateRequest updateCategoryBdata= categoryRequestUtils.new CategoryUpdateRequestBuilder(categoryB.getId())
				.parent(categoryA).build();
		
		Category expectedCategoryB = categoryService.update(updateCategoryBdata);
		Category expectedCategoryA = categoryService.findById(categoryA.getId());

		assertThat(expectedCategoryA.getParentCategory()).isNull();
		assertThat(expectedCategoryB.getParentCategory().getId()).isEqualTo(categoryA.getId());
	}

	@Test
	public void testItDeletesACategoryImage() {
		Category categoryToUpdate = categoryGenerator.saveRandomCategoryOnDb();
		CategoryUpdateRequest newCategoryData = categoryRequestUtils.new CategoryUpdateRequestBuilder(
				categoryToUpdate.getId()).withRandomName().imageIdToDelete(categoryToUpdate.getImage().getId()).build();

		Category updatedCategory = categoryService.update(newCategoryData);

		assertThat(updatedCategory.getImage()).isNull();
	}

	@Test(expected = EntityNotFoundException.class)
	public void delete() {
		Category categoryToDelete = categoryGenerator.saveRandomCategoryOnDb();

		categoryService.deleteById(categoryToDelete.getId());

		// this should throw an exception
		categoryService.findById(categoryToDelete.getId());
	}

	private void assertThatTwoCategoriesAreEqual(Category c1, Category c2) {
		assertThat(c1.getName()).isEqualTo(c2.getName());
		assertThat(c1.getParentCategory()).isEqualTo(c2.getParentCategory());
	}
}

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
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest extends BaseTest {

  @Autowired
  CategoryService categoryService;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  CategoryTestsUtils categoryUtils;

  @Test
  public void testItfindsAllCategories() {
    long CATEGORIES_SIZE = 4;
    List<Category> categories = categoryUtils.saveRandomCategoriesOnDb(CATEGORIES_SIZE);

    List<Category> foundCategories = categoryService.findAll();

    assertThat(foundCategories.size()).isEqualTo(categories.size());
  }

  @Test
  public void testItfindACategoryById() {
    Category category = categoryUtils.saveRandomCategoryOnDb();

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
  // @Transactional(TxType.NOT_SUPPORTED)
  public void testItupdatesACategoryWithForm() {
    Category categoryToUpdate = categoryUtils.saveRandomCategoryOnDb(); // THIS IS IN SYNC WITH THE
                                                                        // UPDATED CATEGORY
    CategoryUpdateForm newCategoryDataForm =
        categoryUtils.generateRandomCategoryUpdateForm(categoryToUpdate.getId());

    Category updatedCategory = categoryService.update(newCategoryDataForm);

    assertThatTwoCategoriesAreEqual(updatedCategory, newCategoryDataForm.getModel());
    assertThat(updatedCategory.getId()).isEqualTo(newCategoryDataForm.getId());
    assertThat(updatedCategory.getImage()).isNotNull();
    assertThat(updatedCategory.getImage().getId())
        .isNotEqualTo(categoryToUpdate.getImage().getId());
  }

  @Test
  public void testItCorrectlyUpdatesTheParentCategory() {
    Category categoryA = categoryUtils.saveRandomCategoryOnDb();
    Category categoryB = categoryUtils.saveRandomCategoryOnDb();

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
    Category categoryToUpdate = categoryUtils.saveRandomCategoryOnDb();
    CategoryUpdateForm newCategoryData =
        categoryUtils.generateRandomCategoryUpdateForm(categoryToUpdate.getId());
    newCategoryData.setImageIdToDelete(categoryToUpdate.getImage().getId());
    newCategoryData.setNewImage(null);

    Category updatedCategory = categoryService.update(newCategoryData);

    assertThat(updatedCategory.getImage()).isNull();
  }

  @Test(expected = EntityNotFoundException.class)
  public void delete() {
    Category categoryToDelete = categoryUtils.saveRandomCategoryOnDb();

    categoryService.deleteById(categoryToDelete.getId());

    // this should throw an exception
    categoryService.findById(categoryToDelete.getId());
  }

  private void assertThatTwoCategoriesAreEqual(Category c1, Category c2) {
    assertThat(c1.getName()).isEqualTo(c2.getName());
    assertThat(c1.getParentCategory()).isEqualTo(c2.getParentCategory());
  }
}

package com.cristian.simplestore.integration.controllers.category;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.integration.controllers.category.request.AuthenticatedCategoryRequest;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.utils.CategoryTestFactory;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.RequestBuilder;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest extends BaseIntegrationTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryTestFactory categoryFactory;
  
  @Autowired
  private AuthenticatedCategoryRequest categoryRequest;

  @Test
  public void testItFindsAllCategories()
      throws JsonParseException, JsonMappingException, IOException {
    long MAX_CATEGORIES_SIZE = 4;
    List<Category> categories = categoryFactory.saveRandomCategoriesOnDb(MAX_CATEGORIES_SIZE);

    ResponseEntity<String> response = categoryRequest.sendFindAllCategoriesRequest();

    List<?> responseCategories =
        (List<?>) RequestBuilder.getContentFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseCategories.size()).isEqualTo(categories.size());
  }

  @Test
  public void testItFindsACategoryById()
      throws JsonParseException, JsonMappingException, IOException {
    Category category = categoryFactory.saveRandomCategoryOnDb();

    ResponseEntity<String> response = categoryRequest.sendFindCategoryByIdRequest(category.getId());

    CategoryResponse foundCategory = (CategoryResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), CategoryResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundCategory.getName()).isEqualTo(category.getName());
    assertThat(foundCategory.getId()).isEqualTo(category.getId());
  }

  @Test
  public void testItDoesNotFindACategoryById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentCategoryId = 1L;
    ResponseEntity<String> response = categoryRequest.sendFindCategoryByIdRequest(nonExistentCategoryId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItCreatesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    MultiPartFormBuilder form = categoryFactory.generateRandomCategoryCreateRequestForm();

    ResponseEntity<String> response = categoryRequest.sendCategoryCreateRequest(form);
    CategoryResponse createdCategory = (CategoryResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), CategoryResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatDtoisEqualToForm(createdCategory, form);
    assertThat(createdCategory.getImage()).isNotNull();
  }

  @Test
  public void testItUpdatesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category categoryToUpdate = categoryFactory.saveRandomCategoryOnDb();
    MultiPartFormBuilder form = categoryFactory.generateRandomCategoryUpdateRequesForm();

    ResponseEntity<String> response = categoryRequest.sendCategoryUpdateRequest(categoryToUpdate.getId(), form);
    CategoryResponse updatedCategory = (CategoryResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), CategoryResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThatDtoisEqualToForm(updatedCategory, form);
    assertThat(updatedCategory.getImage().getId())
        .isNotEqualTo(categoryToUpdate.getImage().getId());
  }

  @Test
  public void testItCorrectlyUpdatesTheParentCategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category categoryA = categoryFactory.saveRandomCategoryOnDb();
    Category categoryB = categoryFactory.saveRandomCategoryOnDb();

    // TODO: violation of tests boundary
    categoryA.setParentCategory(categoryB);
    categoryA = categoryRepository.save(categoryA);

    // here we manually create a circular reference
    // Between the category A and its parent, the category B.
    // example B -> A -> B is not allowed
    // it should update to null -> A -> B
    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", categoryB.getName()).add("parentCategory", categoryA.getId());

    ResponseEntity<String> response = categoryRequest.sendCategoryUpdateRequest(categoryB.getId(), form);
    CategoryResponse updatedCategory = (CategoryResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), CategoryResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(((Integer) updatedCategory.getParentCategory().get("id")).longValue())
        .isEqualTo(categoryA.getId());
    
    // TODO: violation of tests boundary 
    categoryA = categoryRepository.findById(categoryA.getId()).get();
    assertThat(categoryA.getParentCategory()).isNull();

  }

  @Test
  public void testItReturnsNotFoundWhenUpdating()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentCategoryId = 1L;

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", "some name");

    ResponseEntity<String> response = categoryRequest.sendCategoryUpdateRequest(nonExistentCategoryId, form);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test(expected = NoSuchElementException.class)
  public void testItDeletesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category category = categoryFactory.saveRandomCategoryOnDb();

    ResponseEntity<String> response = categoryRequest.sendCategoryDeleteRequest(category.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    // TODO: violation of test boundary
    categoryRepository.findById(category.getId()).get();
  }

  @Test
  public void testItReturnsNotFoundWhenDeleting()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

    ResponseEntity<String> response = categoryRequest.sendCategoryDeleteRequest(nonExistentId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private void assertThatDtoisEqualToForm(CategoryResponse categoryDto, MultiPartFormBuilder form) {
    assertThat(categoryDto.getName()).isEqualTo(form.get("name"));
    assertThat(((Integer) categoryDto.getParentCategory().get("id")).longValue())
        .isEqualTo(form.get("parentCategory"));
  }
}

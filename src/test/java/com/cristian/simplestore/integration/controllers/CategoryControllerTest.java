package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.utils.ApiRequestUtils;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.utils.FormBuilder;
import com.cristian.simplestore.web.dto.CategoryResponseDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest extends BaseTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryTestsUtils categoryUtils;

  @Autowired
  DbCleaner dbCleaner;

  private ApiRequestUtils apiUtils;

  @Before
  public void setUp() {
    apiUtils = new ApiRequestUtils(restTemplate);
    cleanUpDb();
  }

  @After
  public void tearDown() {
    cleanUpDb();
  }

  public void cleanUpDb() {
    dbCleaner.cleanCategoriesTable();
    dbCleaner.cleanImagesTable();
  }

  @Test
  public void testItFindsAllCategories()
      throws JsonParseException, JsonMappingException, IOException {
    long MAX_CATEGORIES_SIZE = 4;
    List<Category> categories = categoryUtils.saveRandomCategoriesOnDb(MAX_CATEGORIES_SIZE);

    ResponseEntity<String> response = sendFindAllCategoriesRequest();

    List<?> responseCategories =
        (List<?>) apiUtils.getContentFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseCategories.size()).isEqualTo(categories.size());
  }

  @Test
  public void testItFindsACategoryById()
      throws JsonParseException, JsonMappingException, IOException {
    Category category = categoryUtils.saveRandomCategoryOnDb();

    ResponseEntity<String> response = sendFindCategoryByIdRequest(category.getId());

    CategoryResponseDto foundCategory = (CategoryResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), CategoryResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundCategory.getName()).isEqualTo(category.getName());
    assertThat(foundCategory.getId()).isEqualTo(category.getId());
  }

  @Test
  public void testItDoesNotFindACategoryById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentCategoryId = 1L;
    ResponseEntity<String> response = sendFindCategoryByIdRequest(nonExistentCategoryId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItCreatesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    FormBuilder form = categoryUtils.generateRandomCategoryCreateRequesForm();

    ResponseEntity<String> response = sendCategoryCreateRequest(form);
    CategoryResponseDto createdCategory = (CategoryResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), CategoryResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatDtoisEqualToForm(createdCategory, form);
    assertThat(createdCategory.getImage()).isNotNull();
  }

  @Test
  public void testItUpdatesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category categoryToUpdate = categoryUtils.saveRandomCategoryOnDb();
    FormBuilder form = categoryUtils.generateRandomCategoryUpdateRequesForm();

    ResponseEntity<String> response = sendCategoryUpdateRequest(categoryToUpdate.getId(), form);
    CategoryResponseDto updatedCategory = (CategoryResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), CategoryResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThatDtoisEqualToForm(updatedCategory, form);
    assertThat(updatedCategory.getImage().getId())
        .isNotEqualTo(categoryToUpdate.getImage().getId());
  }

  @Test
  public void testItCorrectlyUpdatesTheParentCategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category categoryA = categoryUtils.saveRandomCategoryOnDb();
    Category categoryB = categoryUtils.saveRandomCategoryOnDb();

    categoryA.setParentCategory(categoryB);
    categoryA = categoryRepository.save(categoryA);

    // here we manually create a circular reference
    // Between the category A and its parent, the category B.
    // example B -> A -> B is not allowed
    // it should update to null -> A -> B
    FormBuilder form = new FormBuilder();
    form.add("name", categoryB.getName()).add("parentCategory", categoryA.getId());

    ResponseEntity<String> response = sendCategoryUpdateRequest(categoryB.getId(), form);
    CategoryResponseDto updatedCategory = (CategoryResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), CategoryResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(((Integer) updatedCategory.getParentCategory().get("id")).longValue())
        .isEqualTo(categoryA.getId());

    categoryA = categoryRepository.findById(categoryA.getId()).get();
    assertThat(categoryA.getParentCategory()).isNull();

  }

  @Test
  public void testItReturnsNotFoundWhenUpdating()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentCategoryId = 1L;

    FormBuilder form = new FormBuilder();
    form.add("name", "some name");

    ResponseEntity<String> response = sendCategoryUpdateRequest(nonExistentCategoryId, form);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test(expected = NoSuchElementException.class)
  public void testItDeletesACategory()
      throws JsonParseException, JsonMappingException, IOException {
    Category category = categoryUtils.saveRandomCategoryOnDb();

    ResponseEntity<String> response = sendCategoryDeleteRequest(category.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    categoryRepository.findById(category.getId()).get();
  }

  @Test
  public void testItReturnsNotFoundWhenDeleting()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

    ResponseEntity<String> response = sendCategoryDeleteRequest(nonExistentId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<String> sendFindAllCategoriesRequest()
      throws JsonParseException, JsonMappingException, IOException {
    String url = "/api/admin/categories";
    ResponseEntity<String> response = apiUtils.sendRequest(url, HttpMethod.GET, null, null);
    return response;
  }

  private ResponseEntity<String> sendFindCategoryByIdRequest(Long id)
      throws JsonParseException, JsonMappingException, IOException {
    String url = "/api/admin/categories/" + id;
    ResponseEntity<String> response = apiUtils.sendRequest(url, HttpMethod.GET, null, null);
    return response;
  }

  private ResponseEntity<String> sendCategoryCreateRequest(FormBuilder form)
      throws JsonParseException, JsonMappingException, IOException {
    String url = "/api/admin/categories";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();

    ResponseEntity<String> jsonResponse = apiUtils.sendRequest(url, HttpMethod.POST, headers, body);
    return jsonResponse;
  }

  private ResponseEntity<String> sendCategoryUpdateRequest(Long categoryId, FormBuilder form)
      throws JsonParseException, JsonMappingException, IOException {
    String url = "/api/admin/categories/" + categoryId;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();

    ResponseEntity<String> response = apiUtils.sendRequest(url, HttpMethod.PUT, headers, body);
    return response;
  }

  private ResponseEntity<String> sendCategoryDeleteRequest(Long categoryId)
      throws JsonParseException, JsonMappingException, IOException {
    String url = "/api/admin/categories/" + categoryId;
    ResponseEntity<String> response = apiUtils.sendRequest(url, HttpMethod.DELETE, null, null);
    return response;
  }

  private void assertThatDtoisEqualToForm(CategoryResponseDto categoryDto, FormBuilder form) {
    assertThat(categoryDto.getName()).isEqualTo(form.get("name"));
    assertThat(((Integer) categoryDto.getParentCategory().get("id")).longValue())
        .isEqualTo(form.get("parentCategory"));
  }
}

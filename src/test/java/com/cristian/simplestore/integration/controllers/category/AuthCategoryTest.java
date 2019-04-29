package com.cristian.simplestore.integration.controllers.category;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.integration.controllers.category.request.UnauthenticatedCategoryRequest;
import com.cristian.simplestore.utils.CategoryTestFactory;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthCategoryTest extends BaseTest {

  @Autowired
  private CategoryTestFactory categoryUtils;
  
  @Autowired
  private UnauthenticatedCategoryRequest categoryRequest;

  @Test
  public void testItFailsFindsAllCategoriesWithoutCredentials()
      throws JsonParseException, JsonMappingException, IOException {
    ResponseEntity<String> response = categoryRequest.sendFindAllCategoriesRequest();
    assertResponseHasUnauthorizedStatus(response);
  }

  @Test
  public void testItFailsFindsACategoryByIdWithoutCredentials()
      throws JsonParseException, JsonMappingException, IOException {
    Long id = 1L;

    ResponseEntity<String> response = categoryRequest.sendFindCategoryByIdRequest(id);
    
    assertResponseHasUnauthorizedStatus(response);
  }


  @Test
  public void testItFailsCreateCategoryWithoutCredentials()
      throws JsonParseException, JsonMappingException, IOException {
    MultiPartFormBuilder form = categoryUtils.generateRandomCategoryCreateRequestForm();

    ResponseEntity<String> response = categoryRequest.sendCategoryCreateRequest(form);

    assertResponseHasUnauthorizedStatus(response);
  }

  @Test
  public void testItFailsUpdateCategoryWithoutCredentials()
      throws JsonParseException, JsonMappingException, IOException {
    Long id = 1L;
    MultiPartFormBuilder form = categoryUtils.generateRandomCategoryUpdateRequesForm();

    ResponseEntity<String> response = categoryRequest.sendCategoryUpdateRequest(id, form);
   
    assertResponseHasUnauthorizedStatus(response);
  }

  @Test
  public void testFailsItDeleteCategoryWithoutCredentials()
      throws JsonParseException, JsonMappingException, IOException {
    Long id = 1L;

    ResponseEntity<String> response = categoryRequest.sendCategoryDeleteRequest(id);
    assertResponseHasUnauthorizedStatus(response);
  }
  
  private void assertResponseHasUnauthorizedStatus(ResponseEntity<String> response) {
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }
}

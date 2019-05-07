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
public class PaginatedCategoryControllerTest extends BaseIntegrationTest {

  @Autowired
  private CategoryTestFactory categoryFactory;
  
  @Autowired
  private AuthenticatedCategoryRequest categoryRequest;

  @Test
  public void testItFindsAllCategories()
      throws JsonParseException, JsonMappingException, IOException {
    long MAX_CATEGORIES_SIZE = 50;
    List<Category> categories = categoryFactory.saveRandomCategoriesOnDb(MAX_CATEGORIES_SIZE);

    ResponseEntity<String> response = categoryRequest.sendFindAllCategoriesRequest();

    List<?> responseCategories =
        (List<?>) RequestBuilder.getContentFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseCategories.size()).isEqualTo(categories.size());
  }

  private void assertThatDtoisEqualToForm(CategoryResponse categoryDto, MultiPartFormBuilder form) {
    assertThat(categoryDto.getName()).isEqualTo(form.get("name"));
    assertThat(((Integer) categoryDto.getParentCategory().get("id")).longValue())
        .isEqualTo(form.get("parentCategory"));
  }
}

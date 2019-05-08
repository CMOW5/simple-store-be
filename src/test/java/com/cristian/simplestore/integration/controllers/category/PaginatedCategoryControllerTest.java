package com.cristian.simplestore.integration.controllers.category;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import com.cristian.simplestore.utils.CategoryTestFactory;
import com.cristian.simplestore.utils.RequestBuilder;
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
    long MAX_CATEGORIES_SIZE = 20;
    categoryFactory.saveRandomCategoriesOnDb(MAX_CATEGORIES_SIZE);
    Integer page = 0;
    Integer size = 5;

    ResponseEntity<String> response = categoryRequest.buildFindAllCategoriesRequest()
        .addRequestParam("page", String.valueOf(page)).addRequestParam("size", String.valueOf(size)).send();
    
    List<?> responseCategories =
        (List<?>) RequestBuilder.getContentFromJsonRespose(response.getBody(), List.class);

    Map<?,?> paginator = RequestBuilder.getPaginatorFromJsonRespose(response.getBody(), Map.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseCategories.size()).isEqualTo(size);
    assertThat(paginator.get("currentPage")).isEqualTo(page);
    assertThat(paginator.get("hasMorePages")).isEqualTo(true);
    assertThat(paginator.get("nextPage")).isEqualTo(page + 1);
    assertThat(paginator.get("hasPrevious")).isEqualTo(false);
    assertThat(paginator.get("previousPage")).isEqualTo(null);
    assertThat(paginator.get("pageSize")).isEqualTo(size);
    assertThat(paginator.get("totalPages")).isEqualTo(((int) (MAX_CATEGORIES_SIZE / size)));   
  }
}

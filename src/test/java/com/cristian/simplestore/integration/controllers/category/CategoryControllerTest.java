package com.cristian.simplestore.integration.controllers.category;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.integration.controllers.category.request.AuthenticatedCategoryRequest;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.category.CategoryFormUtils;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest extends BaseIntegrationTest {

    @Autowired
    private CategoryFormUtils categoryFormUtils;
    
    @Autowired
    private CategoryGenerator categoryGenerator;

    @Autowired
    private AuthenticatedCategoryRequest categoryRequest;

    @Test
    public void testItFindsAllCategories() throws JsonParseException, JsonMappingException, IOException {
        int MAX_CATEGORIES_SIZE = 4;
        List<CategoryEntity> categories = categoryGenerator.saveRandomCategoriesOnDb(MAX_CATEGORIES_SIZE);

        JsonResponse response = categoryRequest.sendFindAllCategoriesRequest();

        List<?> responseCategories = (List<?>) response.getContent(List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseCategories.size()).isEqualTo(categories.size());
    }

    @Test
    public void testItFindsACategoryById() throws JsonParseException, JsonMappingException, IOException {
        CategoryEntity category = categoryGenerator.saveRandomCategoryOnDb();

        JsonResponse response = categoryRequest.sendFindCategoryByIdRequest(category.getId());

        CategoryResponse foundCategory = (CategoryResponse) response.getContent(CategoryResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(foundCategory.getName()).isEqualTo(category.getName());
        assertThat(foundCategory.getId()).isEqualTo(category.getId());
    }

    @Test
    public void testItDoesNotFindACategoryById() throws JsonParseException, JsonMappingException, IOException {
        Long nonExistentCategoryId = 1L;
        JsonResponse response = categoryRequest.sendFindCategoryByIdRequest(nonExistentCategoryId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testItCreatesACategory() throws JsonParseException, JsonMappingException, IOException {
        MultiPartFormBuilder form = categoryFormUtils.generateRandomCategoryCreateRequestForm();
        
        JsonResponse response = categoryRequest.sendCategoryCreateRequest(form);
        CategoryResponse createdCategory = (CategoryResponse) response
                .getContent(CategoryResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThatDtoisEqualToForm(createdCategory, form);
        assertThat(createdCategory.getImage()).isNotNull();
    }

    @Test
    public void testItUpdatesACategory() throws JsonParseException, JsonMappingException, IOException {
        CategoryEntity categoryToUpdate = categoryGenerator.saveRandomCategoryOnDb();     
        MultiPartFormBuilder form = categoryFormUtils.generateRandomCategoryUpdateRequestForm();

        JsonResponse response = categoryRequest.sendCategoryUpdateRequest(categoryToUpdate.getId(), form);
        CategoryResponse updatedCategory = (CategoryResponse) response
                .getContent(CategoryResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatDtoisEqualToForm(updatedCategory, form);
        assertThat(updatedCategory.getImage().getId()).isNotEqualTo(categoryToUpdate.getImage().getId());
    }

    @Test
    public void testItReturnsNotFoundWhenUpdating() throws JsonParseException, JsonMappingException, IOException {
        Long nonExistentCategoryId = 1L;

        MultiPartFormBuilder form = new MultiPartFormBuilder();
        form.add("name", "some name");

        JsonResponse response = categoryRequest.sendCategoryUpdateRequest(nonExistentCategoryId, form);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    public void testItDeletesACategory() throws JsonParseException, JsonMappingException, IOException {
        CategoryEntity category = categoryGenerator.saveRandomCategoryOnDb();

        JsonResponse response = categoryRequest.sendCategoryDeleteRequest(category.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
        response = categoryRequest.sendFindCategoryByIdRequest(category.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void assertThatDtoisEqualToForm(CategoryResponse categoryDto, MultiPartFormBuilder form) {
        assertThat(categoryDto.getName()).isEqualTo(form.get("name"));
        assertThat((categoryDto.getParentCategory().getId()))
                .isEqualTo(form.get("parentCategory"));
    }
}

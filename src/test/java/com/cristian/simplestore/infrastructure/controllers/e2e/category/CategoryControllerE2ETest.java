package com.cristian.simplestore.infrastructure.controllers.e2e.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.infrastructure.controllers.BaseIntegrationTest;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder.CategoryFormUtils;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.requestfactory.CategoryRequestFactory;
import com.cristian.simplestore.infrastructure.web.dto.CategoryDto;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.request.JsonRequestSender;
import com.cristian.simplestore.utils.request.JsonResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerE2ETest extends BaseIntegrationTest {

    @Autowired
    private CategoryFormUtils categoryFormUtils;
    
    @Autowired
    private CategoryGenerator categoryGenerator;
    
    @Autowired
    private JsonRequestSender requestSender;
    
    @Test
    public void testItFindsAllCategories() throws IOException {
        int categoriesSize = 4;
        List<Category> categories = categoryGenerator.saveRandomCategoriesOnDb(categoriesSize);

        RequestEntity<?> request = CategoryRequestFactory.createFindAllCategoriesRequest().build();
        JsonResponse response = requestSender.send(request);
        
        List<?> responseCategories = (List<?>) response.getContent(List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseCategories.size()).isEqualTo(categories.size());
    }

    @Test
    public void testItFindsACategoryById() throws IOException {
        Category category = categoryGenerator.saveRandomCategoryOnDb();

        RequestEntity<?> request = CategoryRequestFactory.createFindCategoryByIdRequest(category.getId()).build();
        JsonResponse response = requestSender.send(request);
        
        CategoryDto foundCategory = response.getContent(CategoryDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(foundCategory.getName()).isEqualTo(category.getName());
        assertThat(foundCategory.getId()).isEqualTo(category.getId());
    }

    @Test
    public void testItDoesNotFindACategoryById() {
        Long nonExistentCategoryId = 1L;
        
        RequestEntity<?> request = CategoryRequestFactory.createFindCategoryByIdRequest(nonExistentCategoryId).build();
        JsonResponse response = requestSender.send(request);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testItCreatesACategory() throws IOException {
        MultiPartFormBuilder form = categoryFormUtils.generateRandomCategoryCreateRequestForm();
        
        RequestEntity<?> request = CategoryRequestFactory.createCategoryCreateRequest(form).build();
        JsonResponse response = requestSender.send(request);
        
        CategoryDto createdCategory = response.getContent(CategoryDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThatDtoisEqualToForm(createdCategory, form);
        assertThat(createdCategory.getImage()).isNotNull();
    }

    @Test
    public void testItUpdatesACategory() throws IOException {
        Category categoryToUpdate = categoryGenerator.saveRandomCategoryOnDb();     
        MultiPartFormBuilder form = categoryFormUtils.generateRandomCategoryUpdateRequestForm();
        
        RequestEntity<?> request = CategoryRequestFactory.createCategoryUpdateRequest(categoryToUpdate.getId(), form).build();
        JsonResponse response = requestSender.send(request);
        
        CategoryDto updatedCategory = response.getContent(CategoryDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatDtoisEqualToForm(updatedCategory, form);
        assertThat(updatedCategory.getImage().getId()).isNotEqualTo(categoryToUpdate.getImage().getId());
    }

    @Test
    public void testItReturnsNotFoundWhenUpdating() {
        Long nonExistentCategoryId = 1L;

        MultiPartFormBuilder form = new MultiPartFormBuilder();
        form.add("name", "some name");
        
        RequestEntity<?> request = CategoryRequestFactory.createCategoryUpdateRequest(nonExistentCategoryId, form).build();
        JsonResponse response = requestSender.send(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testItDeletesACategory() {
        Category category = categoryGenerator.saveRandomCategoryOnDb();
        
        RequestEntity<?> request = CategoryRequestFactory.createCategoryDeleteRequest(category.getId()).build();
        JsonResponse response = requestSender.send(request);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        response = requestSender.send(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void assertThatDtoisEqualToForm(CategoryDto categoryDto, MultiPartFormBuilder form) {
        assertThat(categoryDto.getName()).isEqualTo(form.get("name"));
        assertThat((categoryDto.getParent().getId()))
                .isEqualTo(form.get("parentId"));
    }
}
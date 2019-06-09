package com.cristian.simplestore.web.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.business.services.category.CategoryService;
import com.cristian.simplestore.business.services.image.ImageService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.cristian.simplestore.web.pagination.CustomPaginator;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
public class TestController {

  private ApiResponse response = new ApiResponse();

  @Autowired
  ImageService imageService;

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("api/test")
  public ResponseEntity<?> findAllCategories(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size, HttpServletRequest request) {
    Page<Category> result = categoryService.findAll(page, size);
    List<CategoryResponse> categoriesResponse = convertEntitiesToDto(result.getContent());
    CustomPaginator paginator = CustomPaginator.of(result, page, size, request);
    return response.status(HttpStatus.OK).content(categoriesResponse).paginator(paginator)
        .build();
  }

  private CategoryResponse convertEntityToDto(Category category) {
    return new CategoryResponse(category);
  }

  private List<CategoryResponse> convertEntitiesToDto(List<Category> categories) {
    List<CategoryResponse> dtos = new ArrayList<>();
    categories.forEach(category -> dtos.add(convertEntityToDto(category)));
    return dtos;
  }
}

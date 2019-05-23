package com.cristian.simplestore.web.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.business.services.category.CategoryService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.CustomPaginator;
import com.cristian.simplestore.web.CustomPaginatorImpl;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

  private ApiResponse response = new ApiResponse();

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public ResponseEntity<?> findAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
    Page<Category> result = categoryService.findAll(page, size);
    List<CategoryResponse> categoriesResponse = convertEntitiesToDto(result.getContent());
    CustomPaginator paginator = new CustomPaginatorImpl(result);
    return response.status(HttpStatus.OK).content(categoriesResponse).paginator(paginator).build();
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findCategoryById(@PathVariable long id) {
    Category foundCategory = categoryService.findById(id);
    return response.status(HttpStatus.OK).content(convertEntityToDto(foundCategory)).build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid CategoryCreateRequest form) {
    Category createdCategory = categoryService.create(form);
    return response.status(HttpStatus.CREATED).content(convertEntityToDto(createdCategory)).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@Valid CategoryUpdateRequest form) {
    Category updatedCategory = categoryService.update(form);
    return response.status(HttpStatus.OK).content(convertEntityToDto(updatedCategory)).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    categoryService.deleteById(id);
    return response.status(HttpStatus.NO_CONTENT).content(null).build();
  }

  @GetMapping("/count")
  public ResponseEntity<?> count() {
    long categoriesCount = categoryService.count();
    return response.status(HttpStatus.OK).content(categoriesCount).build();
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

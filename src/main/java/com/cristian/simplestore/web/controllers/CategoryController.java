package com.cristian.simplestore.web.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.cristian.simplestore.web.pagination.CustomPaginator;
import com.cristian.simplestore.web.utils.response.ApiResponse;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

  private final CategoryService categoryService;
  
  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<?> findAllCategories(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size, HttpServletRequest request) {
    Page<CategoryEntity> paginatedResult = categoryService.findAll(page, size);
    List<CategoryResponse> categoriesResponse = CategoryResponse.of(paginatedResult.getContent());
    CustomPaginator paginator = CustomPaginator.of(paginatedResult, page, size, request);
    return new ApiResponse().status(HttpStatus.OK).content(categoriesResponse).paginator(paginator).build();
  }


  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findCategoryById(@PathVariable long id) {
    CategoryEntity foundCategory = categoryService.findById(id);
    return new ApiResponse().status(HttpStatus.OK).content(CategoryResponse.of(foundCategory)).build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid CategoryCreateRequest form) {
    CategoryEntity createdCategory = categoryService.create(form);
    return new ApiResponse().status(HttpStatus.CREATED).content(CategoryResponse.of(createdCategory)).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@Valid CategoryUpdateRequest form) {
    CategoryEntity updatedCategory = categoryService.update(form);
    return new ApiResponse().status(HttpStatus.OK).content(CategoryResponse.of(updatedCategory)).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    categoryService.deleteById(id);
    return new ApiResponse().status(HttpStatus.NO_CONTENT).content(null).build();
  }

  @GetMapping("/count")
  public ResponseEntity<?> count() {
    long categoriesCount = categoryService.count();
    return new ApiResponse().status(HttpStatus.OK).content(categoriesCount).build();
  }
}

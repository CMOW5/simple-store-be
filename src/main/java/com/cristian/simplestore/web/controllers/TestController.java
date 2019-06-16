package com.cristian.simplestore.web.controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.business.services.category.CategoryService;
import com.cristian.simplestore.business.services.image.ImageService;
import com.cristian.simplestore.business.services.product.ProductService;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.web.dto.response.CategoryResponse;
import com.cristian.simplestore.web.dto.response.ProductResponse;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
@SuppressWarnings(value = {"all"})
public class TestController {

  private ApiResponse response = new ApiResponse();

  @Autowired
  ImageService imageService;
  
  @Autowired
  ProductService productService;

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("api/test")
  public ResponseEntity<?> findAllCategories(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size, HttpServletRequest request) {
    List<CategoryEntity> entities = categoryService.findAll();
    List<CategoryResponse> categories = CategoryResponse.from(entities);
    List<ProductEntity> entitiesp = productService.findAll();
    List<ProductResponse> products = ProductResponse.from(entitiesp);
    return response.status(HttpStatus.OK).content(products).build();
  }
}

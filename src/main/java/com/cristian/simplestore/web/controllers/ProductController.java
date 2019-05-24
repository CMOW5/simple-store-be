package com.cristian.simplestore.web.controllers;

import java.util.ArrayList;
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
import com.cristian.simplestore.business.services.product.ProductService;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;
import com.cristian.simplestore.web.dto.response.ProductResponse;
import com.cristian.simplestore.web.pagination.CustomPaginator;
import com.cristian.simplestore.web.pagination.CustomPaginatorImpl;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<?> findAllProducts(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size, HttpServletRequest request) {
    Page<Product> paginatedResult = productService.findAll(page, size);
    List<ProductResponse> products = convertEntitiesToDto(paginatedResult.getContent());
    CustomPaginator paginator = new CustomPaginatorImpl(paginatedResult, page, size, request);
    return new ApiResponse().status(HttpStatus.OK).content(products).paginator(paginator).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findProductById(@PathVariable long id) {
    Product product = productService.findById(id);
    return new ApiResponse().status(HttpStatus.OK).content(convertEntityToDto(product)).build();
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid ProductCreateRequest form) {
    Product createdProduct = productService.create(form);
    return new ApiResponse().status(HttpStatus.CREATED).content(convertEntityToDto(createdProduct)).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable long id, @Valid ProductUpdateRequest form) {
    Product updatedProduct = productService.update(form);
    return new ApiResponse().status(HttpStatus.OK).content(convertEntityToDto(updatedProduct)).build();

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable long id) {
    productService.deleteById(id);
    return new ApiResponse().status(HttpStatus.NO_CONTENT).content(null).build();
  }

  @GetMapping("/count")
  public ResponseEntity<?> count() {
    long productsCount = productService.count();
    return new ApiResponse().status(HttpStatus.OK).content(productsCount).build();
  }

  private ProductResponse convertEntityToDto(Product product) {
    return new ProductResponse(product);
  }

  private List<ProductResponse> convertEntitiesToDto(List<Product> products) {
    List<ProductResponse> dtos = new ArrayList<>();
    products.forEach(product -> dtos.add(convertEntityToDto(product)));
    return dtos;
  }
}

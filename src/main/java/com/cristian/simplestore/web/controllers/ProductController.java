package com.cristian.simplestore.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.business.services.ProductService;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	private ApiResponse response = new ApiResponse();

	@GetMapping
	public ResponseEntity<?> findAllProducts() {
		List<Product> products = productService.findAll();
		return response.status(HttpStatus.OK)
				.content(products)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findProductById(@PathVariable long id) {
		Product product = productService.findById(id);
		return response.status(HttpStatus.OK)
				.content(product)
				.build();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid ProductCreateForm form) {
		Product createdProduct = productService.create(form);
		return response.status(HttpStatus.CREATED)
				.content(createdProduct)
				.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable long id, 
			@Valid ProductUpdateForm form) {
		Product updatedProduct = productService.update(form);
		return response.status(HttpStatus.OK)
				.content(updatedProduct)
				.build();
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		productService.deleteById(id);
		return response.status(HttpStatus.NO_CONTENT)
				.content(null)
				.build();
	}

	@GetMapping("/count")
	public ResponseEntity<?> count() {
		long productsCount = productService.count();
		return response.status(HttpStatus.OK)
				.content(productsCount)
				.build();
	}
}

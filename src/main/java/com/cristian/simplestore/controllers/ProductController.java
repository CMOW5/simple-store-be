package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.forms.ProductCreateForm;
import com.cristian.simplestore.forms.ProductUpdateForm;
import com.cristian.simplestore.services.ProductService;
import com.cristian.simplestore.utils.response.CustomResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/admin/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	private CustomResponse<Object> response = new CustomResponse<>();

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllProducts() {
		List<Product> products = productService.findAllProducts();
		return response.status(HttpStatus.OK)
				.content(products)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findProductById(@PathVariable long id) {
		try {
			Product product = productService.findById(id);
			return response.status(HttpStatus.OK)
					.content(product)
					.build();
		} catch (EntityNotFoundException exception) {
			return response.status(HttpStatus.NOT_FOUND)
					.content(null)
					.build();
		} 
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid ProductCreateForm form) {
		Product createdProduct = productService.create(form);
		return response.status(HttpStatus.CREATED)
				.content(createdProduct)
				.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable long id, 
			@Valid ProductUpdateForm form) {
		try {
			Product updatedProduct = productService.update(form);
			return response.status(HttpStatus.OK)
					.content(updatedProduct)
					.build();
		} catch (EntityNotFoundException exception) {
			return response.status(HttpStatus.NOT_FOUND)
					.content(null)
					.build();
		}
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable long id) {
		try {
			productService.deleteById(id);
			return response.status(HttpStatus.NO_CONTENT)
					.content(null)
					.build();
		} catch (EntityNotFoundException exception) {
			return response.status(HttpStatus.NOT_FOUND)
					.content(null)
					.build();
		}
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Object>> count() {
		long productsCount = productService.count();
		return response.status(HttpStatus.OK)
				.content(productsCount)
				.build();
	}
}

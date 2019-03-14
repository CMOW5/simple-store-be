package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

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

		response.status(HttpStatus.OK).content(products);
		return response.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findProductById(@PathVariable long id) {
		Product product = productService.findById(id);

		response.status(HttpStatus.OK).content(product);
		return response.build();
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid ProductCreateForm form) {
		
		Product createdProduct = productService.create(form);

		response.status(HttpStatus.OK).content(createdProduct);
		return response.build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable long id, 
			@Valid ProductUpdateForm form) {

		Product updatedProduct = productService.update(form);

		response.status(HttpStatus.OK).content(updatedProduct);
		return response.build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable long id) {
		productService.deleteById(id);
		
		response.status(HttpStatus.NO_CONTENT).content(null);
		return response.build();
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Object>> count() {
		long productsCount = productService.count();

		response.status(HttpStatus.OK).content(productsCount);
		return response.build();
	}
}

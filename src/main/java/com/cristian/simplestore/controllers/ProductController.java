package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.forms.ProductCreateForm;
import com.cristian.simplestore.forms.ProductUpdateForm;
import com.cristian.simplestore.services.ProductService;
import com.cristian.simplestore.utils.response.CustomResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	private CustomResponse response = new CustomResponse();

	@RequestMapping(value = "/api/admin/products", method = RequestMethod.GET)
	public Map<String, Object> findAllProducts() {
		List<Product> products = productService.findAllProducts();

		response.attachContent(products);
		return response.build();
	}

	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.GET)
	public Map<String, Object> findProductById(@PathVariable long id) {
		Product product = productService.findById(id);

		response.attachContent(product);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/products", method = RequestMethod.POST)
	public Map<String, Object> create(@Valid ProductCreateForm form) {
		
		Product createdProduct = productService.create(form);

		response.attachContent(createdProduct);
		return response.build();
	}

	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable long id, 
			@Valid ProductUpdateForm form) {

		Product updatedProduct = productService.update(form);

		response.attachContent(updatedProduct);
		return response.build();
	}

	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		productService.deleteById(id);
	}

	@RequestMapping(value = "/api/admin/products/count", method = RequestMethod.GET)
	public Map<String, Object> count() {
		long productsCount = productService.count();

		response.attachContent(productsCount);
		return response.build();
	}
}

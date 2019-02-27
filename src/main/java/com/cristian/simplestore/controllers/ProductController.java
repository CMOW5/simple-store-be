package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.services.ProductService;
import com.cristian.simplestore.utils.response.CustomResponse;

@RestController
public class ProductController<K, V> {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/api/admin/products", method = RequestMethod.GET)
	public Map<String, Object> findAllProducts() {
		CustomResponse response = new CustomResponse();
		
		List <Product> products = productService.findAllProducts(); 
		response.attachContent(products);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.GET) 
	public Map<String, Object> findProductById(@PathVariable long id) {
		CustomResponse response = new CustomResponse();
		
		Product product = productService.findById(id);
		response.attachContent(product);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/products", method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody @Valid Product product, BindingResult bindingResult) {
		CustomResponse response = new CustomResponse();
		
		if (bindingResult.hasErrors()) {
            return response.attachContent(bindingResult.getFieldErrors()).build();
        }
		
		Product createdProduct = productService.create(product);
		
		response.attachContent(createdProduct);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable long id, @RequestBody Product product) {
		CustomResponse response = new CustomResponse();
		Product updatedProduct = productService.update(id, product);
		
		response.attachContent(updatedProduct);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/products/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		productService.deleteById(id);
	}
	
	@RequestMapping(value = "/api/admin/products/count", method = RequestMethod.GET)
	public Map<String, Object> count() {
		CustomResponse response = new CustomResponse();
		long productsCount = productService.count();
		
		response.attachContent(productsCount);
		return response.build();	
	}
	
	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	public Page<Product>  test() {
		return productService.test();
	}
}

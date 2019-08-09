package com.cristian.simplestore.infrastructure.web.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.application.product.command.CreateProductCommand;
import com.cristian.simplestore.application.product.command.UpdateProductCommand;
import com.cristian.simplestore.application.product.handler.CreateProductHandler;
import com.cristian.simplestore.application.product.handler.DeleteProductHandler;
import com.cristian.simplestore.application.product.handler.ReadProductHandler;
import com.cristian.simplestore.application.product.handler.UpdateProductHandler;
import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.pagination.Paginator;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.infrastructure.web.dto.ApiResponse;
import com.cristian.simplestore.infrastructure.web.dto.ProductDto;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

	private final CreateProductHandler createProductHandler;
	private final ReadProductHandler readProductHandler;
	private final UpdateProductHandler updateProductHandler;
	private final DeleteProductHandler deleteProductHandler;

	@Autowired
	public ProductController(CreateProductHandler createProductHandler, ReadProductHandler readProductHandler,
			UpdateProductHandler updateProductHandler, DeleteProductHandler deleteProductHandler) {
		this.createProductHandler = createProductHandler;
		this.readProductHandler = readProductHandler;
		this.updateProductHandler = updateProductHandler;
		this.deleteProductHandler = deleteProductHandler;
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, HttpServletRequest request) {
		Paginated<Product> paginatedResult = readProductHandler.findAll(page, size);
		Paginator paginator = RequestPaginator.of(paginatedResult.getPaginator(), request, size, page);
		List<ProductDto> productsDtos = ProductDto.fromDomain(paginatedResult.getContent());
		return new ApiResponse().status(HttpStatus.OK).content(productsDtos).paginator(paginator).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Product foundProduct = readProductHandler.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return new ApiResponse().status(HttpStatus.OK).content(ProductDto.fromDomain(foundProduct)).build();
	}

	@PostMapping
	public ResponseEntity<?> create(CreateProductCommand command) {
		Product createdProduct = createProductHandler.execute(command);
		return new ApiResponse().status(HttpStatus.CREATED).content(ProductDto.fromDomain(createdProduct)).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(UpdateProductCommand command) {
		Product updatedProduct = updateProductHandler.execute(command);
		return new ApiResponse().status(HttpStatus.OK).content(ProductDto.fromDomain(updatedProduct)).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		deleteProductHandler.execute(id);
	}

}

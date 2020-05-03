package com.cristian.simplestore.infrastructure.web.controllers.product;

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

import com.cristian.simplestore.application.product.create.CreateProductCommand;
import com.cristian.simplestore.application.product.create.CreateProductHandler;
import com.cristian.simplestore.application.product.delete.DeleteProductHandler;
import com.cristian.simplestore.application.product.read.ReadProductHandler;
import com.cristian.simplestore.application.product.update.UpdateProductCommand;
import com.cristian.simplestore.application.product.update.UpdateProductHandler;
import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.pagination.Paginator;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.infrastructure.web.controllers.product.dto.ProductDto;
import com.cristian.simplestore.infrastructure.web.controllers.product.dto.ProductMapper;
import com.cristian.simplestore.infrastructure.web.controllers.response.ApiResponse;
import com.cristian.simplestore.infrastructure.web.pagination.RequestPaginator;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

	private final CreateProductHandler createProductHandler;
	private final ReadProductHandler readProductHandler;
	private final UpdateProductHandler updateProductHandler;
	private final DeleteProductHandler deleteProductHandler;
	private final ProductMapper productMapper;

	@Autowired
	public ProductController(CreateProductHandler createProductHandler, ReadProductHandler readProductHandler,
			UpdateProductHandler updateProductHandler, DeleteProductHandler deleteProductHandler,
			ProductMapper productMapper) {
		this.createProductHandler = createProductHandler;
		this.readProductHandler = readProductHandler;
		this.updateProductHandler = updateProductHandler;
		this.deleteProductHandler = deleteProductHandler;
		this.productMapper = productMapper;
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(name = "search", required = false) String searchTerm,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
			HttpServletRequest request) {
		Paginated<Product> paginatedResult = readProductHandler.searchByTerm(searchTerm, page, size);
		Paginator paginator = RequestPaginator.of(paginatedResult.getPaginator(), request, size, page);
		List<ProductDto> productsDtos = productMapper.fromDomain(paginatedResult.getContent());
		return new ApiResponse().status(HttpStatus.OK).content(productsDtos).paginator(paginator).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Product foundProduct = readProductHandler.findById(id).orElseThrow(EntityNotFoundException::new);
		return new ApiResponse().status(HttpStatus.OK).content(productMapper.fromDomain(foundProduct)).build();
	}

	@PostMapping
	public ResponseEntity<?> create(CreateProductCommand command) {
		Product createdProduct = createProductHandler.create(command);
		return new ApiResponse().status(HttpStatus.CREATED).content(productMapper.fromDomain(createdProduct)).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(UpdateProductCommand command) {
		Product updatedProduct = updateProductHandler.update(command);
		return new ApiResponse().status(HttpStatus.OK).content(productMapper.fromDomain(updatedProduct)).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		deleteProductHandler.deleteById(id);
	}

}

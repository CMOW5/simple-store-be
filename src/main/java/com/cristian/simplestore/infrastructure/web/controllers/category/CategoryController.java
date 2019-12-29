package com.cristian.simplestore.infrastructure.web.controllers.category;

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

import com.cristian.simplestore.application.category.create.CreateCategoryCommand;
import com.cristian.simplestore.application.category.create.CreateCategoryHandler;
import com.cristian.simplestore.application.category.delete.DeleteCategoryHandler;
import com.cristian.simplestore.application.category.read.ReadCategoryHandler;
import com.cristian.simplestore.application.category.update.UpdateCategoryCommand;
import com.cristian.simplestore.application.category.update.UpdateCategoryHandler;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.pagination.Paginator;
import com.cristian.simplestore.infrastructure.web.controllers.category.dto.CategoryDto;
import com.cristian.simplestore.infrastructure.web.controllers.response.ApiResponse;
import com.cristian.simplestore.infrastructure.web.pagination.RequestPaginator;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

	private final CreateCategoryHandler createCategoryHandler;
	private final ReadCategoryHandler readCategoryHandler;
	private final UpdateCategoryHandler updateCategoryHandler;
	private final DeleteCategoryHandler deleteCategoryHandler;

	@Autowired
	public CategoryController(CreateCategoryHandler createCategoryHandler, ReadCategoryHandler readCategoryHandler,
			UpdateCategoryHandler updateCategoryHandler, DeleteCategoryHandler deleteCategoryHandler) {
		this.createCategoryHandler = createCategoryHandler;
		this.readCategoryHandler = readCategoryHandler;
		this.updateCategoryHandler = updateCategoryHandler;
		this.deleteCategoryHandler = deleteCategoryHandler;
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, HttpServletRequest request) {		
		Paginated<Category> paginatedResult = readCategoryHandler.findAll(page, size);
		Paginator paginator = RequestPaginator.of(paginatedResult.getPaginator(), request, size, page);
		List<CategoryDto> categoriesDto = CategoryDto.fromDomain(paginatedResult.getContent());
		return new ApiResponse().status(HttpStatus.OK).content(categoriesDto).paginator(paginator).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Category foundCategory = readCategoryHandler.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(foundCategory)).build();
	}

	@PostMapping
	public ResponseEntity<?> create(CreateCategoryCommand command) {
		Category createdCategory = createCategoryHandler.create(command);
		return new ApiResponse().status(HttpStatus.CREATED).content(CategoryDto.fromDomain(createdCategory)).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(UpdateCategoryCommand command) {
		Category updatedCategory = updateCategoryHandler.update(command);
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(updatedCategory)).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		deleteCategoryHandler.deleteById(id);
	}
}

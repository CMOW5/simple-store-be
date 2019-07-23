package com.cristian.simplestore.infrastructure.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cristian.simplestore.application.command.CreateCategoryCommand;
import com.cristian.simplestore.application.command.UpdateCategoryCommand;
import com.cristian.simplestore.application.handler.category.CreateCategoryHandler;
import com.cristian.simplestore.application.handler.category.DeleteCategoryHandler;
import com.cristian.simplestore.application.handler.category.ReadCategoryHandler;
import com.cristian.simplestore.application.handler.category.UpdateCategoryHandler;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.infrastructure.controllers.dto.CategoryDto;
import com.cristian.simplestore.web.utils.response.ApiResponse;

@RestController
@RequestMapping("/api/hex/admin/categories")
public class CategoryControllerHex {

	private final CreateCategoryHandler createCategoryHandler;
	private final ReadCategoryHandler readCategoryHandler;
	private final UpdateCategoryHandler updateCategoryHandler;
	private final DeleteCategoryHandler deleteCategoryHandler;

	@Autowired
	public CategoryControllerHex(CreateCategoryHandler createCategoryHandler, ReadCategoryHandler readCategoryHandler,
			UpdateCategoryHandler updateCategoryHandler, DeleteCategoryHandler deleteCategoryHandler) {
		this.createCategoryHandler = createCategoryHandler;
		this.readCategoryHandler = readCategoryHandler;
		this.updateCategoryHandler = updateCategoryHandler;
		this.deleteCategoryHandler = deleteCategoryHandler;
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Category> foundCategories = readCategoryHandler.findAll();
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(foundCategories)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Category foundCategory = readCategoryHandler.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(foundCategory)).build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(CreateCategoryCommand command) {
		Category createdCategory = createCategoryHandler.execute(command);
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(createdCategory)).build();
	}

	@PutMapping
	public ResponseEntity<?> update(UpdateCategoryCommand command) {
		Category updatedCategory = updateCategoryHandler.execute(command);
		return new ApiResponse().status(HttpStatus.OK).content(CategoryDto.fromDomain(updatedCategory)).build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		deleteCategoryHandler.execute(id);
	}
}

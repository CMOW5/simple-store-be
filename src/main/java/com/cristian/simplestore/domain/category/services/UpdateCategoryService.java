package com.cristian.simplestore.domain.category.services;

import javax.persistence.EntityNotFoundException;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.image.Image;

public class UpdateCategoryService {

	private final CategoryRepository categoryRepository;

	public UpdateCategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public Category update(Long id, String name, Long parentId, Image image) {
		Category storedCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException(id));
		
		Category newParent = resolveNewParent(storedCategory, parentId);
		Image newImage = image != null ? image : storedCategory.getImage();
				
		return categoryRepository.save(new Category(storedCategory.getId(), name, newImage, newParent));
	}
	
	private Category resolveNewParent(Category storedCategory, Long newParentId) {
		if (newParentId == null) return null;
		
		Category newParent = categoryRepository.findById(newParentId).orElseThrow(() -> new EntityNotFoundException());

		if (storedCategory.hasSubcategory(newParent)) {
			return resolveCategoryCircularReference(storedCategory, newParent);
		} else if (storedCategory.equals(newParent)) {
			return storedCategory.getParent();
		} else {
			return newParent;
		}
	}

	/**
	 * here we avoid circular references between the category to update and its sub
	 * categories. for instance null -> A -> B -> C to C -> A -> B -> C is not
	 * allowed, the result will be: null -> C -> A -> B
	 */
	private Category resolveCategoryCircularReference(Category storedCategory, Category newParent) {
		newParent = new Category(newParent.getId(), newParent.getName(), newParent.getImage(),
				storedCategory.getParent());
		// is this necessary or will it update after passing it to category to update?
		return categoryRepository.save(newParent);
	}
}

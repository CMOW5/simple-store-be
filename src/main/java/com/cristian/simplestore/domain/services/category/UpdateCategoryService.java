package com.cristian.simplestore.domain.services.category;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.domain.services.image.CreateImageService;

public class UpdateCategoryService {

	private final CategoryRepository categoryRepository;
	private final CreateImageService createImageService;

	public UpdateCategoryService(CategoryRepository categoryRepository, CreateImageService createImageService) {
		this.categoryRepository = categoryRepository;
		this.createImageService = createImageService;
	}
	
	public Category execute(Long id, String newName, Long newParentId, MultipartFile newImageFile) {
		Category storedCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("The category with the given id was not found"));
		
		Category newParent = resolveNewParent(storedCategory, newParentId);
		Image newImage = resolveNewImage(storedCategory, newImageFile);
				
		return categoryRepository.save(new Category(storedCategory.getId(), newName, newImage, newParent));
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
	
	private Image resolveNewImage(Category storedCategory, MultipartFile newImageFile) {
		if (newImageFile == null) {
			return storedCategory.getImage();
		} else {
			return createImageService.create(newImageFile);
		}
	}
}

package com.cristian.simplestore.application.handler.category;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.command.UpdateCategoryCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.category.UpdateCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.image.DeleteImageService;

@Component
public class UpdateCategoryHandler {

	private final ReadCategoryService readCategoryService;
	private final UpdateCategoryService updateCategoryService;
	private final CreateImageService createImageService;
	private final DeleteImageService deleteImageService;

	@Autowired
	public UpdateCategoryHandler(UpdateCategoryService service, CreateImageService createImageService,
			ReadCategoryService readCategoryService, DeleteImageService deleteImageService) {
		this.updateCategoryService = service;
		this.createImageService = createImageService;
		this.readCategoryService = readCategoryService;
		this.deleteImageService = deleteImageService;
	}

	public Category execute(UpdateCategoryCommand command) {
		Category storedCategory = readCategoryService.execute(command.getId())
				.orElseThrow(() -> new EntityNotFoundException("The category with the given id was not found"));

		// TODO: validate data
		Long id = command.getId();
		String name = command.getName();
		Category parent = resolveNewParent(storedCategory, command.getParent());
		Image image = resolveNewImage(storedCategory, command.getImage());
		Category category = new Category(id, name, image, parent);
		return updateCategoryService.execute(category);
	}
	
	private Category resolveNewParent(Category storedCategory, Category newParent) {
		if (storedCategory.hasSubcategory(newParent)) {
			return resolveCategoryCircularReference(storedCategory, newParent);
		} else if (storedCategory.equals(newParent)) {
			return storedCategory.getParent();
		} else {
			return newParent;
		}
	}
	
	/**
	 * here we avoid circular references between the category
	 * to update and its sub categories. for instance
	 * null -> A -> B -> C to C -> A -> B -> C is not allowed,
     * the result will be: null -> C -> A -> B
	 */
	private Category resolveCategoryCircularReference(Category storedCategory, Category newParent) {
		newParent = new Category(newParent.getId(), newParent.getName(), newParent.getImage(), storedCategory.getParent());
		// is this necessary or will it update after passing it to category to update?
		return updateCategoryService.execute(newParent);
	}

	private Image resolveNewImage(Category storedCategory, MultipartFile newImageFile) {
		Image image = storedCategory.getImage();
		return newImageFile == null ? image : updateImage(image, newImageFile);
	}

	private Image updateImage(Image image, MultipartFile newImageFile) {
		// deleteImageService.execute(image);
		return createImageService.execute(newImageFile);
	}
}

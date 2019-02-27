package com.cristian.simplestore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.entities.Image;
import com.cristian.simplestore.respositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageService imageService;

	public List<Category> findAllCategories() {
		List<Category> foundCategories = new ArrayList<Category>();
		this.categoryRepository.findAll().forEach(foundCategories::add);
		return foundCategories;
	}

	public Category findCategoryById(Long id) {
		Optional<Category> foundCategory = this.categoryRepository.findById(id);
		return foundCategory.orElse(null);
	}

	public Category create(Category category) {
		return this.categoryRepository.save(category);
	}

	public Category create(Category category, MultipartFile file) {
		if (file != null) {
			Image image = this.imageService.createImageRepoFromFile(file);
			category.addImage(image);
		}

		return categoryRepository.save(category);
	}

	public Category update(Long id, Category category) {
		category.setId(id);
		return this.categoryRepository.save(category);
	}

	// TODO: delete the images that are not used from storage after update
	public Category update(Long id, Category category, MultipartFile newImageFile, Long imageIdToDelete) {
		Category storedCategory = this.categoryRepository.findById(id).orElse(null);
		String newName = category.getName();
		Category newParentCategory = category.getParentCategory();

		if (storedCategory == null) {
			return null;
		}
		
		// TODO: generate some validation error here
		if (newParentCategory != null && newParentCategory.getId() == storedCategory.getId()) {
			newParentCategory = null;
		}
		
        // here we avoid circular references between the category
        // to update and its subcategories. for instance
        // null -> A -> B -> C to C -> A -> B -> C is not allowed,
        // the result will be: null -> C -> A -> B
		if (storedCategory.hasSubcategory(newParentCategory)) {
			newParentCategory.setParentCategory(storedCategory.getParentCategory());
			this.categoryRepository.save(newParentCategory);
		}
		
		storedCategory.setName(newName);
		storedCategory.setParentCategory(newParentCategory);

		if (newImageFile != null) {
			Image image = this.imageService.createImageRepoFromFile(newImageFile);
			storedCategory.deleteImage();
			storedCategory.addImage(image);
		} else if (imageIdToDelete != null) { // TODO: verify the imageId
			storedCategory.deleteImage();
		}

		return this.update(id, storedCategory);
	}

	public void deleteById(Long id) {
		this.categoryRepository.deleteById(id);
	}

	public long count() {
		return this.categoryRepository.count();
	}
}

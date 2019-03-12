package com.cristian.simplestore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.entities.Image;
import com.cristian.simplestore.forms.CategoryCreateForm;
import com.cristian.simplestore.forms.CategoryUpdateForm;
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
		return this.categoryRepository.findById(id).orElseGet(() -> null);
	}

	public Category create(Category category) {
		return this.categoryRepository.save(category);
	}
	
	public Category create(CategoryCreateForm form) {
		Category category = new Category();
		category.setName(form.getName());
		category.setParentCategory(form.getParentCategory());
		category = addImageToCategory(category, form.getImage());
		
		return categoryRepository.save(category);
	}

	public Category update(Long id, Category category) {
		category.setId(id);
		return this.categoryRepository.save(category);
	}
	
	// TODO: delete the images that are not used from storage after update
	public Category update(CategoryUpdateForm form) {
		Category storedCategory = this.categoryRepository.findById(form.getId()).get();
		
		String newName = form.getName();
		Category newParentCategory = form.getParentCategory();
		MultipartFile newImageFile = form.getNewImage();
		Long imageIdToDelete = form.getImageIdToDelete();
		
		storedCategory.setName(newName);
		storedCategory = updateParentCategory(storedCategory, newParentCategory);
		storedCategory = updateCategoryImage(storedCategory, newImageFile, imageIdToDelete);
		

		return this.categoryRepository.save(storedCategory);
	}

	public void deleteById(Long id) {
		this.categoryRepository.deleteById(id);
	}

	public long count() {
		return this.categoryRepository.count();
	}
	
	private Category addImageToCategory(Category category, MultipartFile imageFile) {
		if (imageFile != null) {
			Image image = this.imageService.save(imageFile);
			category.addImage(image);
		}
		return category;
	}
	
	private Category updateParentCategory(Category categoryToUpdate, Category newParentCategory) {
		// TODO: generate some validation error here
		if (newParentCategory != null && newParentCategory.getId() == categoryToUpdate.getId()) {
			newParentCategory = null;
		}
						
		// here we avoid circular references between the category
        // to update and its subcategories. for instance
        // null -> A -> B -> C to C -> A -> B -> C is not allowed,
        // the result will be: null -> C -> A -> B
		if (categoryToUpdate.hasSubcategory(newParentCategory)) {
			newParentCategory.setParentCategory(categoryToUpdate.getParentCategory());
			this.categoryRepository.save(newParentCategory);
		}
		
		categoryToUpdate.setParentCategory(newParentCategory);
		
		return categoryToUpdate;
	}
	
	private Category updateCategoryImage(Category categoryToUpdate, MultipartFile newImageFile, Long imageIdToDelete) {
		if (newImageFile != null) {
			Image image = this.imageService.save(newImageFile);
			categoryToUpdate.deleteImage();
			categoryToUpdate.addImage(image);
		} else if (imageIdToDelete != null) { // TODO: verify the imageId
			categoryToUpdate.deleteImage();
		}
		
		return categoryToUpdate;
	}
}

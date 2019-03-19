package com.cristian.simplestore.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageService imageService;

	public List<Category> findAll() {
		List<Category> foundCategories = new ArrayList<Category>();
		this.categoryRepository.findAll().forEach(foundCategories::add);
		return foundCategories;
	}

	public Category findCategoryById(Long id) {
		try {
			Category foundCategory = categoryRepository.findById(id).get();
			return foundCategory;
		} catch (NoSuchElementException e) {
			throw new EntityNotFoundException("The category with the given id was not found");
		}
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
		try {
			Category storedCategory = categoryRepository.findById(form.getId()).get();
			String newName = form.getName();
			Category newParentCategory = form.getParentCategory();
			MultipartFile newImageFile = form.getNewImage();
			Long imageIdToDelete = form.getImageIdToDelete();
			
			storedCategory.setName(newName);
			storedCategory = updateParentCategory(storedCategory, newParentCategory);
			storedCategory = updateCategoryImage(storedCategory, newImageFile, imageIdToDelete);
			return categoryRepository.save(storedCategory);
		} catch (NoSuchElementException e) {
			throw new EntityNotFoundException("The category with the given id was not found");
		}
	}

	public void deleteById(Long id) {
		try {
			this.categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The category with the given id was not found");
		}
		
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
		// TODO: verify if one is null and the other is not
		// TODO: should i throw an exception here?? 
		if (isTheSameCategory(categoryToUpdate, newParentCategory)) {
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
	
	// TODO: replace this with a hasCode and isEquals impl
	private boolean isTheSameCategory(Category category1, Category category2) {
		if ((category1 != null && category2 != null) && (category1.getId() == category2.getId())) {
			return true;
		} else {
			return false;
		}
	}
}

package com.cristian.simplestore.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.image.Image;
import com.cristian.simplestore.image.ImageService;

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
	 
	 public Category create(Category category, MultipartFile image) {
		 Category createdCategory = this.create(category);
		 
		 if (image != null) {
			 createdCategory = addImageFileToCategory(createdCategory, image);
		 }
		 
		 return createdCategory;
	 }
	 
	 public Category addImageFileToCategory(Category category, MultipartFile file) {
		 Image image = this.imageService.createImageRepoFromFile(file);
		 category.addImage(image);
		 return categoryRepository.save(category);
	 }
	 
	 public Category update(Long id, Category category) {
		 category.setId(id);
		 return this.categoryRepository.save(category);
	 }
	 
	 // TODO: delete the images that are not used from storage after update
	 public Category update(Long id, Category category, MultipartFile file, Long imageIdToDelete) {
		 Category storedCategory = this.categoryRepository.findById(id).orElse(null);
		 
		 if (storedCategory == null) return null;
		 /* TODO
		  * if we call update(category) the image is delete because the fe sends it
		  * as null
		  */
		 storedCategory.setName(category.getName());
		 storedCategory.setParentCategory(category.getParentCategory());
		 
		 if (imageIdToDelete != null) {
			 storedCategory.deleteImage();
		 }
		 
		 if (file != null) {
			Image image = this.imageService.createImageRepoFromFile(file);
			storedCategory.addImage(image);
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

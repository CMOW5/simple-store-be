package com.cristian.simplestore.application.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;

@Component
public final class CategoryFactory {
	
	private final ReadCategoryService readCategoryService;
	private final CreateImageService createImageService;
	
	@Autowired
	public CategoryFactory(ReadCategoryService readCategoryService,
			CreateImageService createImageService) {
		this.createImageService = createImageService;
		this.readCategoryService = readCategoryService;
	}
	
	public Category create(String name, MultipartFile imageFile, Long parentId) {
		Image image = createImageService.create(imageFile);
		Category parent = null;
		
		if (parentId != null) {
			parent = readCategoryService.findById(parentId).orElseThrow(() -> new EntityNotFoundException());
		}
		
		return new Category(name, parent, image);
	}
}

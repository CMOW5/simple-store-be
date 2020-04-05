package com.cristian.simplestore.application.category;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.image.Image;

@Component
public final class CategoryFactory {
	
	private final ReadCategoryService readCategoryService;
	private final ImageFactory imageFactory;
	
	@Autowired
	public CategoryFactory(ReadCategoryService readCategoryService,
			ImageFactory imageFactory) {
		this.imageFactory = imageFactory;
		this.readCategoryService = readCategoryService;
	}
	
	public Category create(String name, MultipartFile imageFile, Long parentId) {
		Image image = imageFactory.fromFile(imageFile);
		Category parent = null;
		
		if (parentId != null) {
			parent = readCategoryService.findById(parentId).orElseThrow(EntityNotFoundException::new);
		}
		
		return new Category(name, parent, image);
	}
}

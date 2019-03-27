package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.persistence.entities.Image;

@Component
public class ImageTestsUtils {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ImageBuilder imageBuilder;
	
	public Image saveRandomImageOnDb() {
		MultipartFile imageFile = this.imageBuilder.createMultipartImage();
		return this.imageService.save(imageFile);
	}
	
	public List<Image> saveRandomImagesOnDb(long numberOfImages) {
		List<Image> savedImages = new ArrayList<>();
		
		for (int i = 0; i < numberOfImages; i++) {
			savedImages.add(this.saveRandomImageOnDb());
		}
		
		return savedImages;
	}
	
	public List<MultipartFile> generateRandomMultiPartFiles(long numberOfImages) {
		List<MultipartFile> files = new ArrayList<>();
		
		for (int i = 0; i < numberOfImages; i++) {
			files.add(this.imageBuilder.createMultipartImage());
		}
		
		return files;
	}
}

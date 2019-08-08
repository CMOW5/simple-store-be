package com.cristian.simplestore.application.image;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.storage.StorageService;

@Component
public final class ImageFactory {

	private final StorageService imageStorageService;

	@Autowired
	public ImageFactory(StorageService storageService) {
		imageStorageService = storageService;
	}
	
	public Image fromFile(MultipartFile file) {
		Image image = null;
		
		if (file != null) {
			String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
			image = new Image(imageNameWithPath);
		}
		
		return image;
	}

	public List<Image> fromFiles(List<MultipartFile> files) {
		return files.stream().map(file -> fromFile(file)).collect(Collectors.toList());
	}

	private String generateImageName(MultipartFile file) {
		return UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
	}
}

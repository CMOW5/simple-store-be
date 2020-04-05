package com.cristian.simplestore.infrastructure.web.controllers.image.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.infrastructure.config.properties.ApplicationConfig;
import com.cristian.simplestore.infrastructure.web.controllers.image.ImageController;

@Component
public class ImageMapper {
	
	private final ApplicationConfig appConfig;
	
	@Autowired
	public ImageMapper(ApplicationConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	public ImageDto fromDomain(Image image) {
		if (image == null)
			return null;
		ImageDto dto = new ImageDto();
		dto.setId(image.getId());
		dto.setUrl(convertNameToUrl(image.getFileName()));
		return dto;
	}
	
	public List<ImageDto> fromDomain(List<Image> images) {
		return images.stream()
					.map(this::fromDomain)
					.collect(Collectors.toList());
	}
	
	private String convertNameToUrl(String imageName) {
		return appConfig.getFullPath() + "/" + ImageController.IMAGE_PATH + "/" + removeDirectoryFromImage(imageName);
	}
	
	private String removeDirectoryFromImage(String imageName) {
		String[] parts = imageName.split("/");
		return parts[parts.length - 1];
	}
}

package com.cristian.simplestore.infrastructure.controllers.dto;

import com.cristian.simplestore.domain.models.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
	 private String name;
	 
	 public static ImageDto fromDomain(Image image) {
		 if (image == null) return null;
		 ImageDto dto = new ImageDto();
		 dto.name = image.getName();
		 return dto;
	 }
}

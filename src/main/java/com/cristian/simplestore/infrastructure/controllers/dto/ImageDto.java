package com.cristian.simplestore.infrastructure.controllers.dto;

import java.util.List;
import java.util.stream.Collectors;

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

	public static List<ImageDto> fromDomain(List<Image> images) {
		return images.stream().map(ImageDto::fromDomain).collect(Collectors.toList());
	}
}

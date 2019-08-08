package com.cristian.simplestore.infrastructure.web.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cristian.simplestore.domain.image.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
	 private Long id;
	 private String name;
	 
	 public static ImageDto fromDomain(Image image) {
		 if (image == null) return null;
		 ImageDto dto = new ImageDto();
		 dto.id = image.getId();
		 dto.name = image.getFileName();
		 return dto;
	 }

	public static List<ImageDto> fromDomain(List<Image> images) {
		return images.stream().map(ImageDto::fromDomain).collect(Collectors.toList());
	}
}

package com.cristian.simplestore.web.dto;

import com.cristian.simplestore.persistence.entities.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponseDto {
	private Long id;
	private String url;
	
	public ImageResponseDto(Image image) {
		id = image.getId();
		url = image.getUrl();
	}
}

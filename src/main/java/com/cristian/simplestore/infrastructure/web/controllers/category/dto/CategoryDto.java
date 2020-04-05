package com.cristian.simplestore.infrastructure.web.controllers.category.dto;

import com.cristian.simplestore.infrastructure.web.controllers.image.dto.ImageDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
	private Long id;

	private String name;

	private CategoryDto parent;

	private ImageDto image;
}

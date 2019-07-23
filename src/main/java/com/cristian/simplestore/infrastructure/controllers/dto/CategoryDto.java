package com.cristian.simplestore.infrastructure.controllers.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cristian.simplestore.domain.models.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
	private Long id;

	private String name;

	private CategoryDto parent;

	private ImageDto image;
	
	public static CategoryDto fromDomain(Category category) {
		if (category == null) return null;
		CategoryDto dto = new CategoryDto();
		dto.id = category.getId();
		dto.name = category.getName();
		dto.image = ImageDto.fromDomain(category.getImage());
		dto.parent = fromDomain(category.getParent());
		return dto;
	}

	public static List<CategoryDto> fromDomain(List<Category> foundCategories) {
		return foundCategories.stream().map(CategoryDto::fromDomain).collect(Collectors.toList());
	}
}

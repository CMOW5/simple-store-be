package com.cristian.simplestore.infrastructure.web.controllers.category.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.infrastructure.web.controllers.image.dto.ImageMapper;

@Component
public class CategoryMapper {
	
	private final ImageMapper imageMapper;
	
	public CategoryMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	
	public CategoryDto fromDomain(Category category) {
		if (category == null) return null;
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setImage(imageMapper.fromDomain(category.getImage()));
		dto.setParent(fromDomain(category.getParent()));
		return dto;
	}

	public List<CategoryDto> fromDomain(List<Category> categories) {
		return categories.stream()
				.map(category -> fromDomain(category))
				.collect(Collectors.toList());
	}

}

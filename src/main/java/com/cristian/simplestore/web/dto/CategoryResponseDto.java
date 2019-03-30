package com.cristian.simplestore.web.dto;

import java.util.Objects;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponseDto implements EntityDto<Category> {

	private Long id;

	private String name;

	private ParentCategoryResponseDto parentCategory;

	private ImageResponseDto image;

	public CategoryResponseDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		mapImage(category.getImage());
		mapParentCategory(category.getParentCategory());
	}

	private void mapImage(Image image) {
		if (image != null) {
			this.image = new ImageResponseDto(image);
		}
	}

	private void mapParentCategory(Category parentCategory) {
		if (parentCategory != null) {
			this.parentCategory = new ParentCategoryResponseDto(parentCategory.getId(), parentCategory.getName());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CategoryResponseDto categoryDto = (CategoryResponseDto) o;
		return Objects.equals(name, categoryDto.name) && Objects.equals(id, categoryDto.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}

package com.cristian.simplestore.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.validators.annotations.ExistsDb;

@ExistsDb(
  table = "categories",
  columnName = "name",
  columnValueField = "name"
)
public class CategoryCreateForm {
	
	@NotNull @Size(min = 2, max = 200) 
	private String name;
	
	// Nullable and exists
	private Category parentCategory;
	
	private MultipartFile image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	public Category getModel() {
		Category category = new Category();
		category.setName(name);
		category.setParentCategory(parentCategory);
		return category;
	}
	
}

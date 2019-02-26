package com.cristian.simplestore.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.category.Category;
import com.cristian.simplestore.validators.ExistsDb;

@ExistsDb(
  table = "categories",
  columnName = "name",
  columnValueField = "name",
  exceptIdColumn = "id"
)
public class CategoryUpdateForm {
	
	Long id;
	
	@NotNull @Size(min = 2, max = 200) 
	private String name;
	
	// Nullable and exists
	private Category parentCategory;
	
	private MultipartFile image;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		category.setId(id);
		return category;
	}
	
}


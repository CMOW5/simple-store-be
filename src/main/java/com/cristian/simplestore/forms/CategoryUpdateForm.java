package com.cristian.simplestore.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.validators.annotations.Exists;
import com.cristian.simplestore.validators.annotations.ExistsDb;

@ExistsDb(
  table = "categories",
  columnName = "name",
  columnValueField = "name",
  exceptIdColumn = "id"
)
public class CategoryUpdateForm implements Form<Category> {
	
	@NotNull
	@Exists(table = "categories", column = "id", message = "the category doesn't exists")
	Long id;
	
	@NotNull @Size(min = 2, max = 200) 
	private String name;
	
	private Category parentCategory;
	
	private MultipartFile newImage;
	
	private Long imageIdToDelete;
	
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

	public MultipartFile getNewImage() {
		return newImage;
	}

	public void setNewImage(MultipartFile newImage) {
		this.newImage = newImage;
	}

	public Long getImageIdToDelete() {
		return imageIdToDelete;
	}

	public void setImageIdToDelete(Long imageIdToDelete) {
		this.imageIdToDelete = imageIdToDelete;
	}
	
	@Override
	public Category getModel() {
		Category category = new Category();
		category.setName(name);
		category.setParentCategory(parentCategory);
		category.setId(id);
		return category;
	}
	
}


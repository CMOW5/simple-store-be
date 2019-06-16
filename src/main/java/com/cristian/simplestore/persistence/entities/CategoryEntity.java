package com.cristian.simplestore.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToOne
	private CategoryEntity parentCategory;

	// @OneToMany(mappedBy = "parentCategory" , cascade = CascadeType.ALL,
	// orphanRemoval = true)
	// @JsonIgnore
	// private List<Category> subcategories = new ArrayList<>();

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductEntity> productEntities = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "image_id")
	private ImageEntity image;

	public CategoryEntity(String name) {
		this.name = name;
	}

	public CategoryEntity(String name, ImageEntity image, CategoryEntity parentCategory) {
		this.name = name;
		this.image = image;
		this.parentCategory = parentCategory;
	}

	public void addSubCategory(CategoryEntity subcategory) {
		// this.subcategories.add(subcategory);
		// subcategory.setParentCategory(this);
	}

	public void addProduct(ProductEntity productEntity) {
		productEntities.add(productEntity);
		productEntity.setCategory(this);
	}

	public void deleteImage() {
		image = null;
	}

	/**
	 * verify if the given category is a sub category of the current category
	 * 
	 * @param category
	 * @return true if the given category is a sub category of the current object
	 */
	public boolean hasSubcategory(CategoryEntity category) {
		if (category == null)
			return false;

		CategoryEntity currentCategory = category;

		while (currentCategory.getParentCategory() != null) {
			if (currentCategory.getParentCategory().getId() == id) {
				return true;
			}
			currentCategory = currentCategory.getParentCategory();
		}
		return false;
	}

	public static class Builder {
		String name;
		ImageEntity image;
		CategoryEntity parentCategory;

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder image(ImageEntity image) {
			this.image = image;
			return this;
		}

		public Builder parent(CategoryEntity category) {
			this.parentCategory = category;
			return this;
		}

		public CategoryEntity build() {
			return new CategoryEntity(name, image, parentCategory);
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
		CategoryEntity category = (CategoryEntity) o;
		return Objects.equals(name, category.name) && Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}

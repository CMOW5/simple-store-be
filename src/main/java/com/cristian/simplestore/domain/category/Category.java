package com.cristian.simplestore.domain.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;

public class Category {

	private Long id;

	private String name;

	private Category parent;
		
	private List<Product> products = new ArrayList<>();

	private Image image;

	public Category(Long id, String name, Image image, Category parent) {
		this(name, parent, image);
		this.id = id;
	}

	public Category(String name, Category parent, Image image) {
		this.name = name;
		this.image = image;
		setParent(parent);
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Image getImage() {
		return image;
	}

	public Category getParent() {
		return parent;
	}
	
	public void setParent(Category parent) {
		if (parent == null) {
			this.parent = null;
		} else if (hasSubcategory(parent)) {
			/**
			 * here we avoid circular references between the category to update and its sub
			 * categories. for instance null -> A -> B -> C to C -> A -> B -> C is not
			 * allowed, the result will be: null -> C -> A -> B
			 */
			parent.parent = this.parent; // B.parent = A.parent
			this.parent = parent; // A.parent = B			
		} else if (parent.equals(this)) {
			// update nothing
		} else {
			this.parent = parent;
		}
	}
	
	/**
	 * verify if the given category is a sub category of the current category
	 * 
	 * @param category
	 * @return true if the given category is a sub category of the current object
	 */
	public boolean hasSubcategory(Category category) {
		if (category == null)
			return false;

		Category currentParent = category.getParent();

		while (currentParent != null) {
			if (currentParent.equals(this)) {
				return true;
			}
			currentParent = currentParent.getParent();
		}
		return false;
	}
	
	public void addProduct(Product product) {
		products.add(product);
		product.setCategory(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Category category = (Category) o;
		return Objects.equals(name, category.name) && Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}

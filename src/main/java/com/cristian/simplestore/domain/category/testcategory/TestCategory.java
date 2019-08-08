package com.cristian.simplestore.domain.category.testcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;

public class TestCategory {

	private Long id;

	private CategoryName name;

	private TestCategory parent;
		
	private List<Product> products = new ArrayList<>();

	private Image image;

	public TestCategory(Long id, CategoryName name, Image image, TestCategory parent) {
		this(name, parent, image);
		this.id = id;
	}

	public TestCategory(CategoryName name, TestCategory parent, Image image) {
		this.name = name;
		this.image = image;
		setParent(parent);
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name.getValue();
	}

	public Image getImage() {
		return image;
	}

	public TestCategory getParent() {
		return parent;
	}
	
	public void setParent(TestCategory parent) {
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
	public boolean hasSubcategory(TestCategory category) {
		if (category == null)
			return false;

		TestCategory currentParent = category.getParent();

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
		// product.setCategory(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestCategory category = (TestCategory) o;
		return Objects.equals(getName(), category.getName()) && Objects.equals(id, category.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), id);
	}
}

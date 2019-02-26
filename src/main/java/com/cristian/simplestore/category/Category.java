package com.cristian.simplestore.category;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.ForeignKey;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cristian.simplestore.image.Image;
import com.cristian.simplestore.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false, unique = true) 
	private String name;
	
	@ManyToOne
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory" ,cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Category> subcategories = new ArrayList<>();
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(
			name = "image_id",
			foreignKey = @ForeignKey(name = "IMAGE_ID_FK")
	)
	private Image image;
	
	public Category() {}
	
	public Category(String name) {
		this.name = name;
	}
	
	public Category(long id) {
		this.id = id;
	}

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
	
	public void addSubCategory(Category subcategory) {
		this.subcategories.add(subcategory);
		subcategory.setParentCategory(this);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
		product.setCategory(this);
	}
	
	public void addImage(Image image) {
		this.image = image;
	}
	
	public void deleteImage() {
		this.image = null;
	}
	
	public Image getImage() {
		return this.image;
	}
	
}

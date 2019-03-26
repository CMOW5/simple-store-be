package com.cristian.simplestore.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private double price;

	private double priceSale;

	@ColumnDefault("false")
	private boolean inSale;

	@ColumnDefault("true")
	private boolean active;

	@ManyToOne
	private Category category;

	@OneToMany(
			mappedBy = "product", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true
	)
	private List<ProductImage> images = new ArrayList<>();
	
	private Long stock;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public Product() {
	}

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPriceSale() {
		return priceSale;
	}

	public void setPriceSale(double priceSale) {
		this.priceSale = priceSale;
	}

	public boolean isInSale() {
		return inSale;
	}

	public void setInSale(boolean inSale) {
		this.inSale = inSale;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	public List<Image> getImages() {
		List<Image> images = new ArrayList<Image>();
		List<ProductImage> productImages = this.images;
		productImages.forEach(image -> images.add(image.getImage()));
		return images;
	}

	public void addImage(Image image) {
		ProductImage productImage = new ProductImage(this, image);
		images.add(productImage);
		image.getOwners().add(productImage);
	}
	
	public void addImages(List<Image> images) {
		for (Image image : images) {
			addImage(image);
		}
	}

	public void removeImage(Image image) {
		ProductImage productImage = new ProductImage(this, image);
		image.getOwners().remove(productImage);
		images.remove(productImage);
		productImage.setProduct(null);
		productImage.setImage(null);
	}
	
	public void removeImages(List<Image> images) {
		for (Image image: images) {
			this.removeImage(image);
		}
	}
	
}

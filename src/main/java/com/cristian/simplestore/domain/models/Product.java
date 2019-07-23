package com.cristian.simplestore.domain.models;

import java.util.List;
import java.util.Objects;

public class Product {
	private Long id;

	private String name;

	private String description;

	private double price;

	private double priceSale;

	private boolean inSale;

	private boolean active;

	private Category category;

	private List<Image> images;

	private long stock;

	public Product(Long id, String name, String description, double price, double priceSale, boolean inSale,
			boolean active, Category category, List<Image> images, long stock) {
		this(name, description, price, priceSale, inSale, active, category, images, stock);
		this.id = id;
	}

	public Product(String name, String description, double price, double priceSale, boolean inSale, boolean active,
			Category category, List<Image> images, long stock) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.priceSale = priceSale;
		this.inSale = inSale;
		this.active = active;
		this.category = category;
		this.images = images;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public double getPriceSale() {
		return priceSale;
	}

	public boolean isInSale() {
		return inSale;
	}

	public boolean isActive() {
		return active;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Image> getImages() {
		return images;
	}

	public long getStock() {
		return stock;
	}

	public void addImages(List<Image> images) {
		this.images.addAll(images);
	}

	public void removeImages(List<Image> images) {
		this.images.removeAll(images);
	}
	
	public void removeImagesById(List<Long> imagesIds) {
		imagesIds.forEach((id) -> {
			this.images.removeIf((image) -> image.getId() == id);
		});
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(name, product.name) && Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}

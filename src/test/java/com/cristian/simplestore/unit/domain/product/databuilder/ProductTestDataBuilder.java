package com.cristian.simplestore.unit.domain.product.databuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;

public class ProductTestDataBuilder {
	private Long id;
	private String name = "product name";
	private String description = "product description";
	private double price = 3000;
	private double priceSale = 2000;
	private boolean inSale = false;
	private boolean active = true;
	private Category category;
	private List<Image> images = new ArrayList<>();
	private long stock = 15;

	public ProductTestDataBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public ProductTestDataBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ProductTestDataBuilder description(String description) {
		this.description = description;
		return this;
	}

	public ProductTestDataBuilder price(double price) {
		this.price = price;
		return this;
	}

	public ProductTestDataBuilder priceSale(double priceSale) {
		this.priceSale = priceSale;
		return this;
	}

	public ProductTestDataBuilder inSale(boolean inSale) {
		this.inSale = inSale;
		return this;
	}

	public ProductTestDataBuilder active(boolean active) {
		this.active = active;
		return this;
	}

	public ProductTestDataBuilder category(Category category) {
		this.category = category;
		return this;
	}

	public ProductTestDataBuilder images(List<Image> images) {
		this.images = images;
		return this;
	}

	public ProductTestDataBuilder stock(long stock) {
		this.stock = stock;
		return this;
	}

	public Product build() {
		return new Product(id, name, description, price, priceSale, inSale, active, category, images, stock);
	}

	public static List<Product> createProducts(int size) {
		return Stream.iterate(size, i -> i++).map((i) -> new ProductTestDataBuilder().build()).limit(size)
				.collect(Collectors.toList());
	}
}

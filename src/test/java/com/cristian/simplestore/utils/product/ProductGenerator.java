package com.cristian.simplestore.utils.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.ImageTestsUtils;
import com.github.javafaker.Faker;

@Component
public class ProductGenerator {

	private static double MAX_PRICE = 10000000000.0;
	private static int MAX_STOCK = 100000000;

	private ProductRepository productRepository;
	private ImageTestsUtils imageUtils;
	private CategoryGenerator categoryGenerator;
	private Faker faker = new Faker();

	@Autowired
	public ProductGenerator(ProductRepository productRepository ,ImageTestsUtils imageUtils, CategoryGenerator categoryGenerator) {
		this.productRepository = productRepository;
		this.imageUtils = imageUtils;
		this.categoryGenerator = categoryGenerator;
	}

	public String generateRandomName() {
		return faker.name().name();
	}

	public String generateRandomDescription() {
		return faker.lorem().sentence();
	}

	public double generateRandomPrice() {
		return Double.valueOf(faker.commerce().price(0, MAX_PRICE));
	}

	public long generateRandomStock() {
		return (long) faker.number().numberBetween(0, MAX_STOCK);
	}
	
	public ProductEntity generateRandomProduct() {
		return new Builder().randomName().randomDescription().randomPrice().randomPriceSale().randomCategory()
				.randomStock().randomImages(2).randomCategory().active().build();
	}
	
	public ProductEntity saveRandomProductOnDB() {
		return productRepository.save(generateRandomProduct());
	}
	
	public List<ProductEntity> saveRandomProductsOnDB(int size) {
		List<ProductEntity> productEntities = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			productEntities.add(saveRandomProductOnDB());
		}
		return productEntities;
	}

	public class Builder {
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		private CategoryEntity category;
		private Long stock;
		private List<ImageEntity> images = new ArrayList<>();

		public Builder randomName() {
			this.name = generateRandomName();
			return this;
		}

		public Builder randomDescription() {
			this.description = generateRandomDescription();
			return this;
		}

		public Builder randomPrice() {
			this.price = generateRandomPrice();
			return this;
		}

		public Builder randomPriceSale() {
			this.priceSale = generateRandomPrice();
			return this;
		}

		public Builder randomStock() {
			this.stock = generateRandomStock();
			return this;
		}

		public Builder inSale() {
			this.inSale = true;
			return this;
		}

		public Builder active() {
			this.active = true;
			return this;
		}

		public Builder randomCategory() {
			this.category = categoryGenerator.saveRandomCategoryOnDb();
			return this;
		}

		public Builder randomImages(int size) {
			this.images = imageUtils.saveRandomImagesOnDb(size);
			return this;
		}

		public ProductEntity build() {
			ProductEntity productEntity = new ProductEntity();
			productEntity.setName(name);
			productEntity.setDescription(description);
			productEntity.setPrice(price);
			productEntity.setPriceSale(priceSale);
			productEntity.setStock(stock);
			productEntity.setInSale(inSale);
			productEntity.setActive(active);
			productEntity.setCategory(category);
			productEntity.addImages(images);
			return productEntity;
		}
		
		public ProductEntity save() {
			return productRepository.save(build());
		}
	}
}

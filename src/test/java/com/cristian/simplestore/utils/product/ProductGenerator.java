package com.cristian.simplestore.utils.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.MockImageFileFactory;
import com.github.javafaker.Faker;

@Component
public class ProductGenerator {

	private static double MAX_PRICE = 10000000000.0;
	private static int MAX_STOCK = 100000000;

	private ProductRepository productRepository;
	private ImageFactory imageFactory;
	private CategoryGenerator categoryGenerator;
	private Faker faker = new Faker();

	@Autowired
	public ProductGenerator(ProductRepository productRepository, ImageFactory imageFactory, CategoryGenerator categoryGenerator) {
		this.productRepository = productRepository;
		this.imageFactory = imageFactory;
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
	
	public Product generateRandomProduct() {
		return new Builder().randomName().randomDescription().randomPrice().randomPriceSale().randomCategory()
				.randomStock().randomImages(2).randomCategory().active().build();
	}
	
	public Product saveRandomProductOnDB() {
		return productRepository.save(generateRandomProduct());
	}
	
	public List<Product> saveRandomProductsOnDB(int size) {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			products.add(saveRandomProductOnDB());
		}
		return products;
	}

	public class Builder {
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		private Category category;
		private Long stock;
		private List<Image> images = new ArrayList<>();

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
			for (int i = 0; i < size; i++) {
				this.images.add(imageFactory.fromFile(MockImageFileFactory.createMockMultiPartFile()));
			}
			return this;
		}

		public Product build() {
			return new Product(name, description, price, priceSale, inSale, active, category, images, stock);
		}
		
		public Product save() {
			return productRepository.save(build());
		}
	}
}
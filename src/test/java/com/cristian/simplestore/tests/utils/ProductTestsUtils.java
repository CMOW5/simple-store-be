package com.cristian.simplestore.tests.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.respositories.ProductRepository;
import com.github.javafaker.Faker;

@Component
public class ProductTestsUtils {
	
	@Autowired
	private ProductRepository productRepository;
	
	private Faker faker = new Faker();
	
	public static double MAX_PRICE = 10000000000.0;
	
	/**
	 * create a product instance with random data
	 * @return the newly created product
	 */
	public Product generateRandomProduct() {
		Product product = new Product();
		
		String name = faker.commerce().productName();
		String description = faker.commerce().productName();
		String price = faker.commerce().price(0, Double.MAX_VALUE); 
		String priceSale = faker.commerce().price(0, Double.MAX_VALUE);
		boolean inSale = false;
		boolean active = true;
		Category category = null;
		Long units = (long) faker.number().numberBetween(0, 200);

		product.setName(name);
		product.setDescription(description);
		product.setPrice(Double.valueOf(price));
		product.setPriceSale(Double.valueOf(priceSale));
		product.setInSale(inSale);
		product.setActive(active);
		product.setCategory(category);
		product.setUnits(units);
		
		return product;
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly created product
	 */
	public Product saveRandomProductOnDB() {
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, MAX_PRICE); 
		return productRepository.save(new Product(name, Double.valueOf(price)));
	}
	
	/**
	 * stores several random product into the database
	 * @param numberOfProducts
	 * @return
	 */
	public List<Product> saveRandomProductsOnDB(long numberOfProducts) {
		List<Product> products = new ArrayList<>();
		
		for (int i = 0; i < numberOfProducts; i++) {
			products.add(saveRandomProductOnDB());
		}
		
		return products;
	}
}

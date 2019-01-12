package com.cristian.simplestore.tests.product;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.product.Product;
import com.cristian.simplestore.product.ProductRepository;
import com.github.javafaker.Faker;

@Service
@Configurable
public class ProductTestUtils {
	
	public static final double MAX_PRICE = 10000000000.0;
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * create a product instance with random data
	 * @return the newly created user
	 */
	public Product createRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, Double.MAX_VALUE); 
		return new Product(name, Double.valueOf(price));
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly created product
	 */
	public Product saveRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, MAX_PRICE); 
		return productRepository.save(new Product(name, Double.valueOf(price)));
	}
}

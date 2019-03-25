package com.cristian.simplestore.utils;

import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;
import com.github.javafaker.Faker;

@Component
public class ProductFactory {
	
	private Faker faker = new Faker();	
	private static double MAX_PRICE = 10000000000.0;

	public Product generateRandomProduct() {
		Product product = new Product();
		
		String name = faker.commerce().productName();
		String description = faker.commerce().productName();
		String price = faker.commerce().price(0, MAX_PRICE); 
		String priceSale = faker.commerce().price(0, MAX_PRICE);
		boolean inSale = false;
		boolean active = true;
		Category category = null;
		Long stock = (long) faker.number().numberBetween(0, 200);

		product.setName(name);
		product.setDescription(description);
		product.setPrice(Double.valueOf(price));
		product.setPriceSale(Double.valueOf(priceSale));
		product.setInSale(inSale);
		product.setActive(active);
		product.setCategory(category);
		product.setStock(stock);
		
		return product;
	}
	
	public ProductCreateForm generateRandomProductCreateForm() {
		ProductCreateForm form = new ProductCreateForm();
		
		String name = faker.commerce().productName();
		String description = faker.lorem().sentence();
		Double price = Double.valueOf(faker.commerce().price(0, Double.MAX_VALUE)); 
		Double priceSale = Double.valueOf(faker.commerce().price(0, Double.MAX_VALUE)); 
		boolean inSale = false;
		boolean active = true;
		Category category = null;
		Long stock = (long) faker.number().numberBetween(0, 200);
		
		form.setName(name);
		form.setDescription(description);
		form.setPrice(price);
		form.setPriceSale(priceSale);
		form.setInSale(inSale);
		form.setActive(active);
		form.setCategory(category);
		form.setStock(stock);
		
		return form;
	}
	
	public ProductUpdateForm generateRandomProductUpdateForm() {
		ProductUpdateForm form = new ProductUpdateForm();
		
		String name = faker.commerce().productName();
		String description = faker.lorem().sentence();
		Double price = Double.valueOf(faker.commerce().price(0, Double.MAX_VALUE)); 
		Double priceSale = Double.valueOf(faker.commerce().price(0, Double.MAX_VALUE)); 
		boolean inSale = false;
		boolean active = true;
		Category category = null;
		Long stock = (long) faker.number().numberBetween(0, 200);
		
		form.setName(name);
		form.setDescription(description);
		form.setPrice(price);
		form.setPriceSale(priceSale);
		form.setInSale(inSale);
		form.setActive(active);
		form.setCategory(category);
		form.setStock(stock);
		
		return form;
	}
}

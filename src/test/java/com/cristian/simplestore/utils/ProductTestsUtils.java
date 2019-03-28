package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;

@Component
public class ProductTestsUtils {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductFactory productFactory;
	
	@Autowired
	private ImageTestsUtils imageUtils;
				
	/**
	 * create a product instance with random data
	 * @return the newly created product
	 */
	public Product generateRandomProduct() {
		return productFactory.generateRandomProduct();
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly saved product
	 */
	public Product saveRandomProductOnDB() {
		return productRepository.save(generateRandomProduct());
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly saved product
	 */
	public Product saveRandomProductOnDBWithImages() {
		List<Image> images = imageUtils.saveRandomImagesOnDb(2);
		Product product = productFactory.generateRandomProduct();
		product.addImages(images);
		return productRepository.save(product);
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
	
	public ProductCreateForm generateRandomProductCreateForm() {
		return productFactory.generateRandomProductCreateForm();
	}
	
	public ProductUpdateForm generateRandomProductUpdateForm(Long id) {
		return productFactory.generateRandomProductUpdateForm(id);
	}
}

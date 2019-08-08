package com.cristian.simplestore.application.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.application.product.command.CreateProductCommand;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;

@Component
public final class ProductFactory {
	
	private final ImageFactory imageFactory;
	private final ReadCategoryService readCategoryService;
	
	@Autowired
	public ProductFactory(ReadCategoryService readCategoryService, ImageFactory imageFactory) {
		this.readCategoryService = readCategoryService;
		this.imageFactory = imageFactory;
	}

	public Product create(CreateProductCommand command) {
		// TODO: validate data
		String name = command.getName();
		String description = command.getDescription();
		double price = command.getPrice();
		double priceSale = command.getPriceSale();
		boolean inSale = command.isInSale();
		boolean active = command.isActive();
		Category category = readCategoryService.findById(command.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException(command.getCategoryId()));
		List<Image> images = imageFactory.fromFiles(command.getImages());
		long stock = command.getStock();
		return new Product(name, description, price, priceSale, inSale, active, category, images, stock);
	}

}

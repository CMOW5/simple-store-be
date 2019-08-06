package com.cristian.simplestore.application.product;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.product.command.CreateProductCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;

@Component
public final class ProductFactory {
	
	private final CreateImageService createImageService;
	private final ReadCategoryService readCategoryService;
	
	@Autowired
	public ProductFactory(ReadCategoryService readCategoryService, CreateImageService createImageService) {
		this.readCategoryService = readCategoryService;
		this.createImageService = createImageService;
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
				.orElseThrow(() -> new EntityNotFoundException());
		long stock = command.getStock();
		List<Image> images = createImageService.create(command.getImages());
		return new Product(name, description, price, priceSale, inSale, active, category, images, stock);
	}

}

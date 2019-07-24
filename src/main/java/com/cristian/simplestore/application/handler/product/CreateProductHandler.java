package com.cristian.simplestore.application.handler.product;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.application.command.CreateProductCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.product.CreateProductService;

@Component
public class CreateProductHandler {

	private final CreateProductService createProductService;
	private final CreateImageService createImageService;
	private final ReadCategoryService readCategoryService;

	@Autowired
	public CreateProductHandler(CreateProductService createProductService, CreateImageService createImageService,
			ReadCategoryService readCategoryService) {
		this.createProductService = createProductService;
		this.createImageService = createImageService;
		this.readCategoryService = readCategoryService;
	}

	public Product execute(CreateProductCommand command) {
		Product product = mapCommandToProduct(command);
		return createProductService.execute(product);
	}

	private Product mapCommandToProduct(CreateProductCommand command) {
		// TODO: validate data
		String name = command.getName();
		String description = command.getDescription();
		double price = command.getPrice();
		double priceSale = command.getPriceSale();
		boolean inSale = command.isInSale();
		boolean active = command.isActive();
		Category category = readCategoryService.findById(command.getCategoryId()).orElseThrow(() -> new EntityNotFoundException());
		long stock = command.getStock();
		List<Image> images = createImageService.create(command.getImages());
		return new Product(name, description, price, priceSale, inSale, active, category, images, stock);
	}
}

package com.cristian.simplestore.application.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.application.product.create.CreateProductCommand;
import com.cristian.simplestore.application.product.update.UpdateProductCommand;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.exceptions.ProductNotFoundException;
import com.cristian.simplestore.domain.product.service.ReadProductService;

@Component
public final class ProductFactory {
	
	private final ImageFactory imageFactory;
	private final ReadCategoryService readCategoryService;
	private final ReadProductService readProductService;
	
	@Autowired
	public ProductFactory(ReadCategoryService readCategoryService, ReadProductService readProductService, ImageFactory imageFactory) {
		this.readCategoryService = readCategoryService;
		this.readProductService = readProductService;
		this.imageFactory = imageFactory;
	}

	public Product create(CreateProductCommand command) {
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
	
	public Product create(UpdateProductCommand command) {
		Product storedProduct = readProductService.findById(command.getId())
				.orElseThrow(() -> new ProductNotFoundException(command.getId()));
		
		Long id = storedProduct.getId();
		String name = command.getName();
		String description = command.getDescription();
		double price = command.getPrice();
		double priceSale = command.getPriceSale();
		boolean inSale = command.isInSale();
		boolean active = command.isActive();
		Category category = readCategoryService.findById(command.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException(command.getCategoryId()));
		long stock = command.getStock();
		
		List<Image> newImages = imageFactory.fromFiles(command.getImages());
		storedProduct.addImages(newImages);
		storedProduct.removeImagesById(command.getImagesIdsToDelete());

		return new Product(id, name, description, price, priceSale, inSale, active, category,
				storedProduct.getImages(), stock);
	}

}

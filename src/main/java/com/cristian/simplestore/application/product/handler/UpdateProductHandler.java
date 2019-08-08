package com.cristian.simplestore.application.product.handler;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.application.product.command.UpdateProductCommand;
import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.exceptions.CategoryNotFoundException;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.exceptions.ProductNotFoundException;
import com.cristian.simplestore.domain.product.service.ReadProductService;
import com.cristian.simplestore.domain.product.service.UpdateProductService;

@Component
public class UpdateProductHandler {

	private final ReadProductService readProductService;
	private final UpdateProductService updateProductService;
	private final ReadCategoryService readCategoryService;
	private final ImageFactory imageFactory;

	@Autowired
	public UpdateProductHandler(ReadProductService readProductService, UpdateProductService updateProductService,
			ReadCategoryService readCategoryService, ImageFactory imageFactory) {
		this.readProductService = readProductService;
		this.updateProductService = updateProductService;
		this.readCategoryService = readCategoryService;
		this.imageFactory = imageFactory;
	}

	@Transactional
	public Product execute(UpdateProductCommand command) {
		Product storedProduct = readProductService.execute(command.getId())
				.orElseThrow(() -> new ProductNotFoundException(command.getId()));
		// validate first
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

		Product product = new Product(id, name, description, price, priceSale, inSale, active, category,
				storedProduct.getImages(), stock);

		return updateProductService.execute(product);
	}
}

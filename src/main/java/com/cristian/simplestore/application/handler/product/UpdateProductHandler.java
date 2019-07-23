package com.cristian.simplestore.application.handler.product;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.application.command.UpdateProductCommand;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.domain.services.product.ReadProductService;
import com.cristian.simplestore.domain.services.product.UpdateProductService;

@Component
public class UpdateProductHandler {

	private final ReadProductService readProductService;
	private final UpdateProductService updateProductService;
	private final CreateImageService createImageService;

	@Autowired
	public UpdateProductHandler(ReadProductService readProductService, UpdateProductService updateProductService,
			CreateImageService createImageService) {
		this.readProductService = readProductService;
		this.updateProductService = updateProductService;
		this.createImageService = createImageService;
	}

	public Product execute(UpdateProductCommand command) {
		Product storedProduct = readProductService.execute(command.getId())
				.orElseThrow(() -> new EntityNotFoundException("The product with the given id was not found"));
		// validate first
		Long id = storedProduct.getId();
		String name = command.getName();
		String description = command.getDescription();
		double price = command.getPrice();
		double priceSale = command.getPriceSale();
		boolean inSale = command.isInSale();
		boolean active = command.isActive();
		Category category = command.getCategory();
		long stock = command.getStock();

		List<Image> newImages = createImageService.create(command.getImages());
		storedProduct.addImages(newImages);
		storedProduct.removeImagesById(command.getImagesIdsToDelete());
		
		Product product = new Product(id, name, description, price, priceSale, inSale, active, category, storedProduct.getImages(), stock);

		return updateProductService.execute(product);
	}
}

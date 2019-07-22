package com.cristian.simplestore.infrastructure.controllers.dto;

import java.util.List;

import com.cristian.simplestore.domain.models.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
	private Long id;

	private String name;

	private String description;

	private double price;

	private double priceSale;

	private boolean inSale;

	private boolean active;

	private CategoryDto category;

	private List<ImageDto> images;

	private long stock;
	
	public static ProductDto fromDomain(Product product) {
		ProductDto dto = new ProductDto();
		dto.id = product.getId();
		dto.name = product.getName();
		dto.description = product.getDescription();
		dto.price = product.getPrice();
		dto.priceSale = product.getPriceSale();
		dto.inSale = product.isInSale();
		dto.active = product.isActive();
		dto.stock = product.getStock();
		dto.category = CategoryDto.fromDomain(product.getCategory());
		dto.images = ImageDto.fromDomain(product.getImages());
		return dto;
	}
}

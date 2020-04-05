package com.cristian.simplestore.infrastructure.web.controllers.product.dto;

import java.util.List;
import com.cristian.simplestore.infrastructure.web.controllers.category.dto.CategoryDto;
import com.cristian.simplestore.infrastructure.web.controllers.image.dto.ImageDto;

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
}

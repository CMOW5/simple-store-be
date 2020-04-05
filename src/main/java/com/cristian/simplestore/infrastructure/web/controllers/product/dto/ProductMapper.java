package com.cristian.simplestore.infrastructure.web.controllers.product.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.infrastructure.web.controllers.category.dto.CategoryMapper;
import com.cristian.simplestore.infrastructure.web.controllers.image.dto.ImageMapper;

@Component
public class ProductMapper {
	
	private final ImageMapper imageMapper;
	
	private final CategoryMapper categoryMapper;
	
	public ProductMapper(ImageMapper imageMapper, CategoryMapper categoryMapper) {
		this.imageMapper = imageMapper;
		this.categoryMapper = categoryMapper;
	}

	public ProductDto fromDomain(Product product) {
		ProductDto dto = new ProductDto();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setPriceSale(product.getPriceSale());
		dto.setInSale(product.isInSale());
		dto.setActive(product.isActive());
		dto.setStock(product.getStock());
		dto.setCategory(categoryMapper.fromDomain(product.getCategory()));
		dto.setImages(imageMapper.fromDomain(product.getImages()));
		return dto;
	}

	public List<ProductDto> fromDomain(List<Product> products) {
		return products.stream()
				.map(product -> fromDomain(product))
				.collect(Collectors.toList());
	}

}

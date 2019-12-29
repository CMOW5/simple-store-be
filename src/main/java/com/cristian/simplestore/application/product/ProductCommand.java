package com.cristian.simplestore.application.product;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public abstract class ProductCommand {
	@NotNull
	@Size(min = 3, max = 100)
	private String name;

	private String description;
	
	@NotNull
	@PositiveOrZero
	private double price;
	
	@NotNull
	@PositiveOrZero
	private double priceSale;

	private boolean inSale;

	private boolean active;
	
	@NotNull
	private Long categoryId;

	private List<MultipartFile> images = new ArrayList<>();

	@NotNull
	@PositiveOrZero
	private Long stock;
}

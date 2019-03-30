package com.cristian.simplestore.web.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;

import lombok.Data;

@ExistsDb(
	table = "products",
	columnName = "name",
	columnValueField = "name"
)
@Data
public class ProductCreateForm implements Form<Product> {
		
	@NotNull @Size(min = 2, max = 100) 
	private String name;
	
	@Size(min = 2, max = 200)
	private String description;

	@PositiveOrZero
	private double price;
	
	@PositiveOrZero
	private double priceSale;
	
	private boolean inSale;
	
	private boolean active;
	
	private Category category;
	
	private List<MultipartFile> images = new ArrayList<MultipartFile>();

	private Long stock;
	
	public void setImages(List<MultipartFile> images) {
		if (images == null) {
			this.images = new ArrayList<>();
		} else {
			this.images = images;
		}
	}

	@Override
	public Product getModel() {
		Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setPriceSale(priceSale);
		product.setInSale(inSale);
		product.setActive(active);
		product.setCategory(category);
		product.setStock(stock);
		return product;
	}
}

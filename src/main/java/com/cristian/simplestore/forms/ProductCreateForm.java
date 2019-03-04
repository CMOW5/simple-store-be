package com.cristian.simplestore.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.validators.annotations.ExistsDb;

@ExistsDb(
	table = "products",
	columnName = "name",
	columnValueField = "name"
)
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

	private Long units;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPriceSale() {
		return priceSale;
	}

	public void setPriceSale(double priceSale) {
		this.priceSale = priceSale;
	}

	public boolean isInSale() {
		return inSale;
	}

	public void setInSale(boolean inSale) {
		this.inSale = inSale;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
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
		return product;
	}
	
	
}

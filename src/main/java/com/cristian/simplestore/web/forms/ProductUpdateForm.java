package com.cristian.simplestore.web.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.validators.annotations.Exists;
import com.cristian.simplestore.web.validators.annotations.ExistsDb;

import lombok.Data;

@ExistsDb(
	table = "products",
	columnName = "name",
	columnValueField = "name",
	exceptIdColumn = "id"
)
@Data
public class ProductUpdateForm implements Form<Product> {
		
	@NotNull
	@Exists(table = "products", column = "id", message = "the product doesn't exists")
	Long id;
	
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
	
	private Long stock;
	
	private List<MultipartFile> newImages = new ArrayList<>();
	
	private List<Long> imagesIdsToDelete = new ArrayList<>();
	
	public void setNewImages(List<MultipartFile> newImages) {
		if (newImages == null) {
			this.newImages = new ArrayList<>();
		} else {
			this.newImages = newImages;
		}
	}

	public List<Long> getImagesIdsToDelete() {
		return imagesIdsToDelete;
	}

	public void setImagesIdsToDelete(List<Long> imagesIdsToDelete) {
		this.imagesIdsToDelete = imagesIdsToDelete;
	}

	@Override
	public Product getModel() {
		Product product = new Product();
		product.setId(id);
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

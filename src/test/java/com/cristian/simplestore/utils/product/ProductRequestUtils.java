package com.cristian.simplestore.utils.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.ImageTestsUtils;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;

@Component
public class ProductRequestUtils {

	private ProductGenerator productGenerator;
	private CategoryGenerator categoryGenerator;
	private ImageTestsUtils imageUtils;

	@Autowired
	public ProductRequestUtils(ProductGenerator productGenerator, CategoryGenerator categoryGenerator,
			ImageTestsUtils imageUtils) {
		this.productGenerator = productGenerator;
		this.categoryGenerator = categoryGenerator;
		this.imageUtils = imageUtils;
	}

	public ProductCreateRequest generateRandomProductCreateForm() {
		return new ProductCreateRequestBuilder().randomName().randomDescription().randomPrice().randomPriceSale()
				.randomStock().randomCategory().randomImages(2).active().build();
	}

	public ProductUpdateRequest generateRandomProductUpdateRequest(Long id) {
		return new ProductUpdateRequestBuilder(id).randomName().randomDescription().randomPrice().randomPriceSale()
				.randomStock().randomCategory().randomImages(2).inSale().active().build();		
	}

	public class ProductCreateRequestBuilder {
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		private CategoryEntity category;
		private Long stock;
		private List<MultipartFile> images = new ArrayList<>();

		public ProductCreateRequestBuilder randomName() {
			this.name = productGenerator.generateRandomName();
			return this;
		}

		public ProductCreateRequestBuilder randomDescription() {
			this.description = productGenerator.generateRandomDescription();
			return this;
		}

		public ProductCreateRequestBuilder randomPrice() {
			this.price = productGenerator.generateRandomPrice();
			return this;
		}

		public ProductCreateRequestBuilder randomPriceSale() {
			this.priceSale = productGenerator.generateRandomPrice();
			return this;
		}

		public ProductCreateRequestBuilder randomStock() {
			this.stock = productGenerator.generateRandomStock();
			return this;
		}

		public ProductCreateRequestBuilder inSale() {
			this.inSale = true;
			return this;
		}

		public ProductCreateRequestBuilder active() {
			this.active = true;
			return this;
		}

		public ProductCreateRequestBuilder randomCategory() {
			this.category = categoryGenerator.saveRandomCategoryOnDb();
			return this;
		}

		public ProductCreateRequestBuilder randomImages(int size) {
			this.images = imageUtils.generateMockMultiPartFiles(size);
			return this;
		}

		public ProductCreateRequest build() {
			ProductCreateRequest productCreateRequest = new ProductCreateRequest();
			productCreateRequest.setName(name);
			productCreateRequest.setDescription(description);
			productCreateRequest.setPrice(price);
			productCreateRequest.setPriceSale(priceSale);
			productCreateRequest.setStock(stock);
			productCreateRequest.setInSale(inSale);
			productCreateRequest.setActive(active);
			productCreateRequest.setCategory(category);
			productCreateRequest.setImages(images);
			return productCreateRequest;
		}
	}

	public class ProductUpdateRequestBuilder {
		Long id;
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		private CategoryEntity category;
		private Long stock;
		private List<MultipartFile> newImages = new ArrayList<>();
		private List<Long> imagesIdsToDelete = new ArrayList<>();

		public ProductUpdateRequestBuilder(Long id) {
			this.id = id;
		}

		public ProductUpdateRequestBuilder randomName() {
			this.name = productGenerator.generateRandomName();
			return this;
		}

		public ProductUpdateRequestBuilder randomDescription() {
			this.description = productGenerator.generateRandomDescription();
			return this;
		}

		public ProductUpdateRequestBuilder randomPrice() {
			this.price = productGenerator.generateRandomPrice();
			return this;
		}

		public ProductUpdateRequestBuilder randomPriceSale() {
			this.priceSale = productGenerator.generateRandomPrice();
			return this;
		}

		public ProductUpdateRequestBuilder randomStock() {
			this.stock = productGenerator.generateRandomStock();
			return this;
		}

		public ProductUpdateRequestBuilder inSale() {
			this.inSale = true;
			return this;
		}

		public ProductUpdateRequestBuilder active() {
			this.active = true;
			return this;
		}

		public ProductUpdateRequestBuilder randomCategory() {
			this.category = categoryGenerator.saveRandomCategoryOnDb();
			return this;
		}

		public ProductUpdateRequestBuilder randomImages(int size) {
			this.newImages = imageUtils.generateMockMultiPartFiles(size);
			return this;
		}

		public ProductUpdateRequestBuilder imagesIdsToDelete(List<Long> imagesIdsToDelete) {
			this.imagesIdsToDelete = imagesIdsToDelete;
			return this;
		}

		public ProductUpdateRequest build() {
			ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
			productUpdateRequest.setId(id);
			productUpdateRequest.setName(name);
			productUpdateRequest.setDescription(description);
			productUpdateRequest.setPrice(price);
			productUpdateRequest.setPriceSale(priceSale);
			productUpdateRequest.setStock(stock);
			productUpdateRequest.setInSale(inSale);
			productUpdateRequest.setActive(active);
			productUpdateRequest.setCategory(category);
			productUpdateRequest.setNewImages(newImages);
			productUpdateRequest.setImagesIdsToDelete(imagesIdsToDelete);
			return productUpdateRequest;
		}
	}
}

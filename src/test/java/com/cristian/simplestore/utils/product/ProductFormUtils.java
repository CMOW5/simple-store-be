package com.cristian.simplestore.utils.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.ImageTestsUtils;

@Component
public class ProductFormUtils {

	private ImageTestsUtils imageUtils;

	private CategoryGenerator categoryGenerator;

	private ProductGenerator productGenerator;

	@Autowired
	private ProductFormUtils(ProductGenerator productGenerator, CategoryGenerator categoryGenerator,
			ImageTestsUtils imageUtils) {
		this.productGenerator = productGenerator;
		this.categoryGenerator = categoryGenerator;
		this.imageUtils = imageUtils;
	}

	public MultiPartFormBuilder generateRandomProductCreateRequestForm() {
		return new CreateRequestFormBuilder().randomName().randomDescription().randomPrice().randomPriceSale().active()
				.randomStock().randomCategory().randomImages(2).build();
	}

	public MultiPartFormBuilder generateRandomProductUpdateRequestForm() {
		return new UpdateRequestFormBuilder().randomName().randomDescription().randomPrice().randomPriceSale().active()
				.randomStock().randomCategory().randomImages(2).build();
	}

	// TODO merge this in a single builder with UpdateRequestFormBuilder
	public class CreateRequestFormBuilder {
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		Long categoryId;
		private Long stock;
		private List<Resource> images = new ArrayList<>();

		public CreateRequestFormBuilder randomName() {
			this.name = productGenerator.generateRandomName();
			return this;
		}

		public CreateRequestFormBuilder randomDescription() {
			this.description = productGenerator.generateRandomDescription();
			return this;
		}

		public CreateRequestFormBuilder randomPrice() {
			this.price = productGenerator.generateRandomPrice();
			return this;
		}

		public CreateRequestFormBuilder randomPriceSale() {
			this.priceSale = productGenerator.generateRandomPrice();
			return this;
		}

		public CreateRequestFormBuilder randomStock() {
			this.stock = productGenerator.generateRandomStock();
			return this;
		}

		public CreateRequestFormBuilder inSale() {
			this.inSale = true;
			return this;
		}

		public CreateRequestFormBuilder active() {
			this.active = true;
			return this;
		}

		public CreateRequestFormBuilder randomCategory() {
			this.categoryId = categoryGenerator.saveRandomCategoryOnDb().getId();
			return this;
		}

		public CreateRequestFormBuilder randomImages(int size) {
			this.images = imageUtils.storeImagesOnDisk(size);
			return this;
		}

		public MultiPartFormBuilder build() {
			return new MultiPartFormBuilder().add("name", name).add("description", description).add("price", price)
					.add("priceSale", priceSale).add("inSale", inSale).add("active", active).add("stock", stock)
					.add("category", categoryId).add("images", images);
		}
	}

	// TODO merge this in a single builder with UpdateRequestFormBuilder
	public class UpdateRequestFormBuilder {
		private String name;
		private String description;
		private double price;
		private double priceSale;
		private boolean inSale;
		private boolean active;
		Long categoryId;
		private Long stock;
		private List<Resource> newImages = new ArrayList<>();
		private List<Long> imagesIdsToDelete = new ArrayList<>();

		public UpdateRequestFormBuilder randomName() {
			this.name = productGenerator.generateRandomName();
			return this;
		}

		public UpdateRequestFormBuilder randomDescription() {
			this.description = productGenerator.generateRandomDescription();
			return this;
		}

		public UpdateRequestFormBuilder randomPrice() {
			this.price = productGenerator.generateRandomPrice();
			return this;
		}

		public UpdateRequestFormBuilder randomPriceSale() {
			this.priceSale = productGenerator.generateRandomPrice();
			return this;
		}

		public UpdateRequestFormBuilder randomStock() {
			this.stock = productGenerator.generateRandomStock();
			return this;
		}

		public UpdateRequestFormBuilder inSale() {
			this.inSale = true;
			return this;
		}

		public UpdateRequestFormBuilder active() {
			this.active = true;
			return this;
		}

		public UpdateRequestFormBuilder randomCategory() {
			this.categoryId = categoryGenerator.saveRandomCategoryOnDb().getId();
			return this;
		}

		public UpdateRequestFormBuilder randomImages(int size) {
			this.newImages = imageUtils.storeImagesOnDisk(size);
			return this;
		}

		public UpdateRequestFormBuilder imagesIdsToDelete(List<Long> imagesIdsToDelete) {
			this.imagesIdsToDelete = imagesIdsToDelete;
			return this;
		}

		public MultiPartFormBuilder build() {
			return new MultiPartFormBuilder().add("name", name).add("description", description).add("price", price)
					.add("priceSale", priceSale).add("inSale", inSale).add("active", active).add("stock", stock)
					.add("category", categoryId).add("newImages", newImages)
					.add("imagesIdsToDelete", imagesIdsToDelete);
		}
	}
}

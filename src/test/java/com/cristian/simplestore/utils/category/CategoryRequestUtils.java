package com.cristian.simplestore.utils.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.utils.image.ImageFileFactory;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

@Component
public class CategoryRequestUtils {

	private ImageFileFactory imageFileFactory;

	private CategoryGenerator categoryGenerator;

	@Autowired
	private CategoryRequestUtils(ImageFileFactory imageFileFactory, CategoryGenerator categoryGenerator) {
		this.imageFileFactory = imageFileFactory;
		this.categoryGenerator = categoryGenerator;
	}

	public class CategoryCreateRequestBuilder {
		private String name;
		private Category parentCategory;
		private MultipartFile image;

		public CategoryCreateRequestBuilder withRandomName() {
			this.name = categoryGenerator.generateRandomCategoryName();
			return this;
		}

		public CategoryCreateRequestBuilder withRandomImage() {
			this.image = imageFileFactory.createMockMultipartImage();
			return this;
		}

		public CategoryCreateRequestBuilder withRandomParent() {
			this.parentCategory = categoryGenerator.new Builder().randomName().save();
			return this;
		}

		public CategoryCreateRequest build() {
			CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
			categoryCreateRequest.setName(name);
			categoryCreateRequest.setParentCategory(parentCategory);
			categoryCreateRequest.setImage(image);
			return categoryCreateRequest;
		}
	}

	public class CategoryUpdateRequestBuilder {
		private Long id;
		private String name;
		private Category parentCategory;
		private MultipartFile newImage;
		private Long imageIdToDelete;

		public CategoryUpdateRequestBuilder(Long id) {
			this.id = id;
		}

		public CategoryUpdateRequestBuilder withRandomName() {
			this.name = categoryGenerator.generateRandomCategoryName();
			return this;
		}
		
		public CategoryUpdateRequestBuilder name(String name) {
			this.name = name;
			return this;
		}

		public CategoryUpdateRequestBuilder withRandomImage() {
			this.newImage = imageFileFactory.createMockMultipartImage();
			return this;
		}
		
		public CategoryUpdateRequestBuilder image(MultipartFile image) {
			this.newImage = image;
			return this;
		}

		public CategoryUpdateRequestBuilder withRandomParent() {
			this.parentCategory = categoryGenerator.new Builder().randomName().save();
			return this;
		}
		
		public CategoryUpdateRequestBuilder parent(Category parent) {
			this.parentCategory = parent;
			return this;
		}

		public CategoryUpdateRequestBuilder imageIdToDelete(Long imageIdToDelete) {
			this.imageIdToDelete = imageIdToDelete;
			return this;
		}

		public CategoryUpdateRequest build() {
			CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
			categoryUpdateRequest.setId(id);
			categoryUpdateRequest.setName(name);
			categoryUpdateRequest.setParentCategory(parentCategory);
			categoryUpdateRequest.setNewImage(newImage);
			categoryUpdateRequest.setImageIdToDelete(imageIdToDelete);
			return categoryUpdateRequest;
		}
	}
}

package com.cristian.simplestore.integration.infrastructure.web.category.databuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.ImageFileFactory;
import com.cristian.simplestore.utils.request.MultiPartFormBuilder;

@Component
public class CategoryFormUtils {

	private final ImageFileFactory imageFileFactory;

	private final CategoryGenerator categoryGenerator;

	@Autowired
	private CategoryFormUtils(CategoryGenerator categoryGenerator, ImageFileFactory imageFileFactory) {
		this.categoryGenerator = categoryGenerator;
		this.imageFileFactory = imageFileFactory;
	}
	
	public MultiPartFormBuilder generateRandomCategoryCreateRequestForm() {
	  return new CreateRequestFormBuilder().randomName().randomImage().randomParent().build();
	}
	
	public MultiPartFormBuilder generateRandomCategoryUpdateRequestForm() {
	  return new UpdateRequestFormBuilder().randomName().randomImage().randomParent().build();
	}

	// TODO merge this in a single builder with UpdateRequestFormBuilder
	public class CreateRequestFormBuilder {
		String name;
		Long parentId;
		Resource image;

		public CreateRequestFormBuilder randomName() {
			this.name = categoryGenerator.generateRandomCategoryName();
			return this;
		}

		public CreateRequestFormBuilder randomImage() {
			this.image = imageFileFactory.storeImageFileOnDisk();
			return this;
		}

		public CreateRequestFormBuilder randomParent() {
			this.parentId = categoryGenerator.saveRandomCategoryOnDb().getId();
			return this;
		}

		public MultiPartFormBuilder build() {
			return new MultiPartFormBuilder().add("name", name).add("parentId", parentId).add("image", image);
		}
	}
	
	// TODO: merge this in a single class with CreateRequestFormBuilder
	public class UpdateRequestFormBuilder {
		String name;
		Long parentId;
		Resource image;

		public UpdateRequestFormBuilder randomName() {
			this.name = categoryGenerator.generateRandomCategoryName();
			return this;
		}

		public UpdateRequestFormBuilder randomImage() {
			this.image = imageFileFactory.storeImageFileOnDisk();
			return this;
		}

		public UpdateRequestFormBuilder randomParent() {
			this.parentId = categoryGenerator.saveRandomCategoryOnDb().getId();
			return this;
		}

		public MultiPartFormBuilder build() {
			return new MultiPartFormBuilder().add("name", name).add("parentId", parentId).add("image", image);
		}
	}
}
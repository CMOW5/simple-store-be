package com.cristian.simplestore.infrastructure.controllers.e2e.category.databuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.image.ImageTestUtils;

@Component
public class CategoryFormUtils {

	private ImageTestUtils imageUtils;

	private CategoryGenerator categortyGenerator;

	@Autowired
	private CategoryFormUtils(CategoryGenerator categortyGenerator, ImageTestUtils imageUtils) {
		this.categortyGenerator = categortyGenerator;
		this.imageUtils = imageUtils;
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
			this.name = categortyGenerator.generateRandomCategoryName();
			return this;
		}

		public CreateRequestFormBuilder randomImage() {
			this.image = imageUtils.storeImageOnDisk();
			return this;
		}

		public CreateRequestFormBuilder randomParent() {
			this.parentId = categortyGenerator.saveRandomCategoryOnDb().getId();
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
			this.name = categortyGenerator.generateRandomCategoryName();
			return this;
		}

		public UpdateRequestFormBuilder randomImage() {
			this.image = imageUtils.storeImageOnDisk();
			return this;
		}

		public UpdateRequestFormBuilder randomParent() {
			this.parentId = categortyGenerator.saveRandomCategoryOnDb().getId();
			return this;
		}

		public MultiPartFormBuilder build() {
			return new MultiPartFormBuilder().add("name", name).add("parentId", parentId).add("image", image);
		}
	}
}

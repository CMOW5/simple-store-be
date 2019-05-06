package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;
import com.github.javafaker.Faker;

@Component
public class CategoryTestFactory {

  private CategoryRepository categoryRepository;

  private ImageFileFactory imageFileFactory;
  
  private ImageTestsUtils imageUtils;
  
  private Faker faker = new Faker();
  
  @Autowired
  private CategoryTestFactory(CategoryRepository categoryRepository, ImageFileFactory imageFileFactory,
      ImageTestsUtils imageUtils) {
    this.categoryRepository = categoryRepository;
    this.imageFileFactory = imageFileFactory;
    this.imageUtils = imageUtils;
  }

  /**
   * create a category instance with random data
   * 
   * @return the newly created category
   */
  public Category generateRandomCategory() {
    Category category = new Category(generateRandomName());
    return category;
  }

  public List<Category> generateRandomCategories(int size) {
    List<Category> categories = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      categories.add(generateRandomCategory());
    }
    return categories;
  }

  public CategoryCreateRequest generateRandomCategoryCreateForm() {
    CategoryCreateRequest form = new CategoryCreateRequest();

    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    MultipartFile image = imageFileFactory.createMockMultipartImage();

    form.setName(name);
    form.setParentCategory(parentCategory);
    form.setImage(image);

    return form;
  }

  public CategoryUpdateRequest generateRandomCategoryUpdateForm(Long id) {
    CategoryUpdateRequest form = new CategoryUpdateRequest();

    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    MultipartFile newImage = imageFileFactory.createMockMultipartImage();

    form.setId(id);
    form.setName(name);
    form.setParentCategory(parentCategory);
    form.setNewImage(newImage);

    return form;
  }

  public MultiPartFormBuilder generateRandomCategoryCreateRequestForm() {
    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    Resource image = imageUtils.storeImageOnDisk();

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", name).add("parentCategory", parentCategory.getId()).add("image", image);

    return form;
  }

  public MultiPartFormBuilder generateRandomCategoryUpdateRequesForm() {
    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    Resource image = imageUtils.storeImageOnDisk();

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", name).add("parentCategory", parentCategory.getId()).add("newImage", image);

    return form;
  }

  /**
   * stores a random category into the database
   * 
   * @return the newly created category
   */
  public Category saveRandomCategoryOnDb() {
    String name = generateRandomName();
    Image image = imageUtils.saveRandomImageOnDb();

    Category category = new Category();
    category.setName(name);
    category.setImage(image);

    return categoryRepository.save(category);
  }

  /**
   * stores a random category into the database
   * 
   * @return the newly created category
   */
  public Category saveRandomCategoryOnDbWithParent() {
    Category category = saveRandomCategoryOnDb();
    Category parentCategory = saveRandomCategoryOnDb();
    category.setParentCategory(parentCategory);
    return categoryRepository.save(category);
  }

  /**
   * stores a couple of random categories on the database
   * 
   * @param numberOfCategories
   * @return the newly created categories
   */
  public List<Category> saveRandomCategoriesOnDb(long numberOfCategories) {
    List<Category> savedCategories = new ArrayList<Category>();

    for (int i = 0; i < numberOfCategories; i++) {
      savedCategories.add(saveRandomCategoryOnDb());
    }

    return savedCategories;
  }

  private String generateRandomName() {
    return faker.name().name();
  }
}

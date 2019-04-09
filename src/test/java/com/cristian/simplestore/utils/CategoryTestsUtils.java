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
public class CategoryTestsUtils {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ImageBuilder imageBuilder;

  @Autowired
  private ImageTestsUtils imageUtils;

  private Faker faker = new Faker();

  /**
   * create a category instance with random data
   * 
   * @return the newly created category
   */
  public Category generateRandomCategory() {
    String name = generateRandomName();
    Category category = new Category();
    category.setName(name);
    return category;
  }

  public CategoryCreateRequest generateRandomCategoryCreateForm() {
    CategoryCreateRequest form = new CategoryCreateRequest();

    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    MultipartFile image = imageBuilder.createMockMultipartImage();

    form.setName(name);
    form.setParentCategory(parentCategory);
    form.setImage(image);

    return form;
  }

  public CategoryUpdateRequest generateRandomCategoryUpdateForm(Long id) {
    CategoryUpdateRequest form = new CategoryUpdateRequest();

    String name = generateRandomName();
    Category parentCategory = saveRandomCategoryOnDb();
    MultipartFile newImage = imageBuilder.createMockMultipartImage();

    form.setId(id);
    form.setName(name);
    form.setParentCategory(parentCategory);
    form.setNewImage(newImage);

    return form;
  }

  public MultiPartFormBuilder generateRandomCategoryCreateRequesForm() {
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

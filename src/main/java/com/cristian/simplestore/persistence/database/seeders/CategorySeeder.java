package com.cristian.simplestore.persistence.database.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.persistence.repositories.ImageRepository;
import com.github.javafaker.Faker;

@Service
public class CategorySeeder {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ImageRepository imageRepository;

  private Faker faker;

  public static final long MAX_CATEGORIES = 10;

  public CategorySeeder() {
    faker = new Faker();
  }

  public void seed(int size) {
    for (int i = 0; i < size; i++) {
      try {
        Category category = createRandomCategoryOnDB();
        createRandomCategoriesWithParentOnDB(category, 4);
      } catch (DataIntegrityViolationException e) {}
    }
  }

  public Category createRandomCategoryOnDB() {
    Category category = new Category();
    category.setName(generateRandomName());
    category.setImage(generateRandomImageOnDB());
    return categoryRepository.save(category);
  }

  private void createRandomCategoriesWithParentOnDB(Category parentCategory, long categoriesSize) {
    for (int i = 0; i < categoriesSize; i++) {
      Category category1 = new Category();
      category1.setName(generateRandomName());
      category1.setParentCategory(parentCategory);
      category1.setImage(generateRandomImageOnDB());
      categoryRepository.save(category1);

      Category category2 = new Category();
      category2.setName(generateRandomName());
      category2.setParentCategory(category1);
      category2.setImage(generateRandomImageOnDB());
      categoryRepository.save(category2);
    }
  }

  public Image generateRandomImageOnDB() {
    String baseUrl = "https://picsum.photos/";
    int width = faker.number().numberBetween(200, 800);
    int height = faker.number().numberBetween(200, 800);

    String imageName = baseUrl + width + "/" + height;

    Image image = new Image();
    image.setName(imageName);
    imageRepository.save(image);
    return image;
  }
  
  private String generateRandomName() {
    // return faker.name().name();
    return faker.commerce().department();
  }

}

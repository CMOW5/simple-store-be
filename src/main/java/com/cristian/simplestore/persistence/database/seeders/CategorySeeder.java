package com.cristian.simplestore.persistence.database.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
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
        CategoryEntity category = createRandomCategoryOnDB();
        createRandomCategoriesWithParentOnDB(category, 4);
      } catch (DataIntegrityViolationException e) {}
    }
  }

  public CategoryEntity createRandomCategoryOnDB() {
    CategoryEntity category = new CategoryEntity();
    category.setName(generateRandomName());
    category.setImage(generateRandomImageOnDB());
    return categoryRepository.save(category);
  }

  private void createRandomCategoriesWithParentOnDB(CategoryEntity parentCategory, long categoriesSize) {
    for (int i = 0; i < categoriesSize; i++) {
      CategoryEntity category1 = new CategoryEntity();
      category1.setName(generateRandomName());
      category1.setParentCategory(parentCategory);
      category1.setImage(generateRandomImageOnDB());
      categoryRepository.save(category1);

      CategoryEntity category2 = new CategoryEntity();
      category2.setName(generateRandomName());
      category2.setParentCategory(category1);
      category2.setImage(generateRandomImageOnDB());
      categoryRepository.save(category2);
    }
  }

  public ImageEntity generateRandomImageOnDB() {
    String baseUrl = "https://picsum.photos/";
    int width = faker.number().numberBetween(200, 800);
    int height = faker.number().numberBetween(200, 800);

    String imageName = baseUrl + width + "/" + height;

    ImageEntity image = new ImageEntity();
    image.setName(imageName);
    imageRepository.save(image);
    return image;
  }
  
  private String generateRandomName() {
    // return faker.name().name();
    return faker.commerce().department();
  }

}

package com.cristian.simplestore.infrastructure.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.image.repository.ImageRepository;
import com.github.javafaker.Faker;

@Service
@Transactional
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
  
  @Transactional
  public void seed(int size) {
    for (int i = 0; i < size; i++) {
      try {
        Category category = createRandomCategoryOnDB();
        // createRandomCategoriesWithParentOnDB(category, 4);
      } catch (Exception e) {}
    }
    Category category = new Category("Rebeca Emard", null, generateRandomImageOnDB());
    categoryRepository.save(category);
  }

  public Category createRandomCategoryOnDB() {
    Category category = new Category(generateRandomName(), null, generateRandomImageOnDB());
    return categoryRepository.save(category);
  }

  private void createRandomCategoriesWithParentOnDB(Category parentCategory, long categoriesSize) {
    for (int i = 0; i < categoriesSize; i++) {
      Category category1 = new Category(generateRandomName(), parentCategory, generateRandomImageOnDB());
      categoryRepository.save(category1);
      
      Category category2 = new Category(generateRandomName(), category1, generateRandomImageOnDB());
      categoryRepository.save(category2);
    }
  }

  public Image generateRandomImageOnDB() {
    String baseUrl = "https://picsum.photos/";
    int width = faker.number().numberBetween(200, 800);
    int height = faker.number().numberBetween(200, 800);

    String imageName = baseUrl + width + "/" + height;

    Image image = new Image("upload-dir/test.png");
    imageRepository.save(image);
    return image;
  }
  
  private String generateRandomName() {
    return "dr. " + faker.name().name();
    // return faker.commerce().department();
  }

}

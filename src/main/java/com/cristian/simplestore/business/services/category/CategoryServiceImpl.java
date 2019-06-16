package com.cristian.simplestore.business.services.category;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.business.services.image.ImageService;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final ImageService imageService;

  private static final String CATEGORY_NOT_FOUND_EXCEPTION =
      "The category with the given id was not found";

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository, ImageService imageService) {
    this.categoryRepository = categoryRepository;
    this.imageService = imageService;
  }

  public List<CategoryEntity> findAll() {
    List<CategoryEntity> foundCategories = new ArrayList<>();
    categoryRepository.findAll().forEach(foundCategories::add);
    return foundCategories;
  }

  public Page<CategoryEntity> findAll(int page, int size) {
    return categoryRepository.findAll(PageRequest.of(page, size));
  }

  public CategoryEntity findById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_EXCEPTION));
  }

  public CategoryEntity create(CategoryEntity category) {
    return categoryRepository.save(category);
  }

  @Transactional
  public CategoryEntity create(CategoryCreateRequest form) {
    CategoryEntity category = new CategoryEntity();
    category.setName(form.getName());
    category.setParentCategory(form.getParentCategory());
    addImageToCategory(category, form.getImage());

    return categoryRepository.save(category);
  }

  public CategoryEntity update(Long id, CategoryEntity category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @Transactional
  public CategoryEntity update(CategoryUpdateRequest form) {
    CategoryEntity storedCategory = categoryRepository.findById(form.getId())
        .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_EXCEPTION));
    String newName = form.getName();
    CategoryEntity newParentCategory = form.getParentCategory();
    MultipartFile newImageFile = form.getNewImage();
    Long imageIdToDelete = form.getImageIdToDelete();

    storedCategory.setName(newName);
    updateParentCategory(storedCategory, newParentCategory);
    updateCategoryImage(storedCategory, newImageFile, imageIdToDelete);

    return storedCategory;
  }

  private CategoryEntity updateParentCategory(CategoryEntity categoryToUpdate, CategoryEntity newParentCategory) {
    if (categoryToUpdate.equals(newParentCategory)) {
      newParentCategory = categoryToUpdate.getParentCategory();
    } else if (categoryToUpdate.hasSubcategory(newParentCategory)) {
      // here we avoid circular references between the category
      // to update and its subcategories. for instance
      // null -> A -> B -> C to C -> A -> B -> C is not allowed,
      // the result will be: null -> C -> A -> B
      newParentCategory.setParentCategory(categoryToUpdate.getParentCategory());
      categoryRepository.save(newParentCategory);
    }

    categoryToUpdate.setParentCategory(newParentCategory);

    return categoryToUpdate;
  }

  private CategoryEntity updateCategoryImage(CategoryEntity categoryToUpdate, MultipartFile newImageFile,
      Long imageIdToDelete) {
    if (newImageFile != null) {
      ImageEntity newImage = imageService.save(newImageFile);
      ImageEntity currentImage = categoryToUpdate.getImage();
      categoryToUpdate.setImage(newImage);
      imageService.delete(currentImage);
    } else if (imageIdToDelete != null) { // TODO: verify the imageId
      categoryToUpdate.deleteImage();
      imageService.deleteById(imageIdToDelete);
    }

    return categoryToUpdate;
  }

  public void deleteById(Long id) {
    try {
      categoryRepository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new EntityNotFoundException(CATEGORY_NOT_FOUND_EXCEPTION);
    }

  }

  public long count() {
    return categoryRepository.count();
  }

  private CategoryEntity addImageToCategory(CategoryEntity category, MultipartFile imageFile) {
    if (imageFile != null) {
      ImageEntity image = imageService.save(imageFile);
      category.setImage(image);
    }
    return category;
  }
}

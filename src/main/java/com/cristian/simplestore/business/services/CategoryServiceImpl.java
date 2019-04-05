package com.cristian.simplestore.business.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ImageService imageService;

  public List<Category> findAll() {
    List<Category> foundCategories = new ArrayList<Category>();
    categoryRepository.findAll().forEach(foundCategories::add);
    return foundCategories;
  }

  public Category findById(Long id) {
    Category foundCategory = categoryRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("The category with the given id was not found"));
    return foundCategory;
  }

  public Category create(Category category) {
    return categoryRepository.save(category);
  }

  @Transactional
  public Category create(CategoryCreateRequest form) {
    Category category = new Category();
    category.setName(form.getName());
    category.setParentCategory(form.getParentCategory());
    category = addImageToCategory(category, form.getImage());

    return categoryRepository.save(category);
  }

  public Category update(Long id, Category category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @Transactional
  public Category update(CategoryUpdateRequest form) {
    Category storedCategory = categoryRepository.findById(form.getId()).orElseThrow(
        () -> new EntityNotFoundException("The category with the given id was not found"));
    String newName = form.getName();
    Category newParentCategory = form.getParentCategory();
    MultipartFile newImageFile = form.getNewImage();
    Long imageIdToDelete = form.getImageIdToDelete();

    storedCategory.setName(newName);
    storedCategory = updateParentCategory(storedCategory, newParentCategory);
    storedCategory = updateCategoryImage(storedCategory, newImageFile, imageIdToDelete);

    return storedCategory;
  }

  private Category updateParentCategory(Category categoryToUpdate, Category newParentCategory) {
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

  private Category updateCategoryImage(Category categoryToUpdate, MultipartFile newImageFile,
      Long imageIdToDelete) {
    if (newImageFile != null) {
      Image newImage = imageService.save(newImageFile);
      Image currentImage = categoryToUpdate.getImage();
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
      throw new EntityNotFoundException("The category with the given id was not found");
    }

  }

  public long count() {
    return categoryRepository.count();
  }

  private Category addImageToCategory(Category category, MultipartFile imageFile) {
    if (imageFile != null) {
      Image image = imageService.save(imageFile);
      category.setImage(image);
    }
    return category;
  }
}

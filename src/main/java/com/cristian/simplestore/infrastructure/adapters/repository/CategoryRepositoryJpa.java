package com.cristian.simplestore.infrastructure.adapters.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.CategoryEntity;

@Component
public class CategoryRepositoryJpa implements CategoryRepository {

  private CategoryRepositoryJpaInterface jpaRepo;
  
  @Autowired
  public CategoryRepositoryJpa(CategoryRepositoryJpaInterface jpaRepo) {
    this.jpaRepo = jpaRepo;
  }
  
  @Override
  public Category save(Category category) {
    CategoryEntity entity = CategoryEntity.fromDomain(category);
    CategoryEntity savedEntity = jpaRepo.save(entity);
    return CategoryEntity.toDomain(savedEntity);
  }

  @Override
  public boolean exists(Category category) {
    return find(category).isPresent();
  }

  @Override
  public Optional<Category> find(Category category) {
    CategoryEntity entity = jpaRepo.findByName(category.getName());
    Category foundCategory = CategoryEntity.toDomain(entity);
    return Optional.ofNullable(foundCategory);

  }

  @Override
  public Category update(Category category) {
    CategoryEntity storedCategory = jpaRepo.findByName(category.getName());
    String name = category.getName();
    CategoryEntity parent = CategoryEntity.fromDomain(category.getParent());
    storedCategory.setName(name);
    storedCategory.setParent(parent);
    CategoryEntity updatedEntity = jpaRepo.save(storedCategory);
    return CategoryEntity.toDomain(updatedEntity);
  }

  @Override
  public void delete(Category category) {
    CategoryEntity entity = CategoryEntity.fromDomain(category);
    jpaRepo.delete(entity);
  }

  @Override
  public List<Category> findAll() {
    List<CategoryEntity> entities = jpaRepo.findAll();
    return CategoryEntity.toDomain(entities);
  }

}

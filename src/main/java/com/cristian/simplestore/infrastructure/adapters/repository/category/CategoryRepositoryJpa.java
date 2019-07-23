package com.cristian.simplestore.infrastructure.adapters.repository.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.CategoryEntity;

@Repository
public class CategoryRepositoryJpa implements CategoryRepository {

	private CategoryRepositoryJpaInterface jpaRepo;

	@Autowired
	public CategoryRepositoryJpa(CategoryRepositoryJpaInterface jpaRepo) {
		this.jpaRepo = jpaRepo;
	}

	@Override
	public Optional<Category> find(Category category) {
		return findById(category.getId());
	}
	
	@Override
	public List<Category> findAll() {
		List<CategoryEntity> entities = jpaRepo.findAll();
		return CategoryEntity.toDomain(entities);
	}
	
	@Override
	public Optional<Category> findById(Long id) {
		Optional<CategoryEntity> entity = jpaRepo.findById(id);
		return entity.isPresent() ? Optional.of(CategoryEntity.toDomain(entity.get())) : Optional.empty();
	}
	
	@Override
	public boolean exists(Category category) {
		return find(category).isPresent();
	}
	
	@Override
	public boolean existsByName(String name) {
		return jpaRepo.findByName(name).isPresent();
	}
	
	@Override
	public Category save(Category category) {
		CategoryEntity entity = CategoryEntity.fromDomain(category);
		return CategoryEntity.toDomain(jpaRepo.save(entity));
	}

	@Override
	public void delete(Category category) {
		CategoryEntity entity = CategoryEntity.fromDomain(category);
		jpaRepo.delete(entity);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepo.deleteById(id);
	}
	
	@Override
	public void deleteAll() {
		jpaRepo.deleteAll();
	}
}

package com.cristian.simplestore.infrastructure.database.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.infrastructure.web.pagination.SpringPaginated;

@Repository
public class CategoryRepositoryJpa implements CategoryRepository {

	private CategoryRepositorySpringJpa jpaRepo;

	@Autowired
	public CategoryRepositoryJpa(CategoryRepositorySpringJpa jpaRepo) {
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
	public Paginated<Category> findAll(int page, int size) {
		Page<CategoryEntity> paginated = jpaRepo.findAll(PageRequest.of(page, size));
		List<Category> categories = CategoryEntity.toDomain(paginated.getContent());
		return SpringPaginated.of(paginated, categories);
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

package com.cristian.simplestore.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.cristian.simplestore.persistence.entities.Category;

public interface CategoryRepository {
	//TODO: delete this
	Iterable<Category> findAll();// extends PagingAndSortingRepository<Category, Long> {

	Page<Category> findAll(PageRequest of);

	Optional<Category> findById(Long id);

	Category save(Category category);

	void deleteById(Long id);

	long count();

}

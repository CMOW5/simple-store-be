package com.cristian.simplestore.respositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cristian.simplestore.entities.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}

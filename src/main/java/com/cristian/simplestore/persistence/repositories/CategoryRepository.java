package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.cristian.simplestore.persistence.entities.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}

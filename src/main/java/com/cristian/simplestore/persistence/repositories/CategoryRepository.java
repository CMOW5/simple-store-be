package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.cristian.simplestore.persistence.entities.CategoryEntity;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

}

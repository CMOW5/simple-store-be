package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.cristian.simplestore.persistence.entities.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}

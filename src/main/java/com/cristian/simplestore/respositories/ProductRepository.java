package com.cristian.simplestore.respositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cristian.simplestore.entities.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}

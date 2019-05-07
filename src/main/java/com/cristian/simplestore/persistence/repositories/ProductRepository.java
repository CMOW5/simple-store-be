package com.cristian.simplestore.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cristian.simplestore.persistence.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

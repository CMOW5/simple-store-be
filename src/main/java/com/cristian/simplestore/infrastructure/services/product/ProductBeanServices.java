package com.cristian.simplestore.infrastructure.services.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.product.service.CreateProductService;
import com.cristian.simplestore.domain.product.service.DeleteProductService;
import com.cristian.simplestore.domain.product.service.ReadProductService;
import com.cristian.simplestore.domain.product.service.UpdateProductService;
import com.cristian.simplestore.infrastructure.database.product.ProductRepositoryJpa;

@Configuration
public class ProductBeanServices {
	@Bean
	public CreateProductService createProductService(ProductRepositoryJpa jpaRepo) {
		return new CreateProductService(jpaRepo);
	}
	
	@Bean
	public ReadProductService readProductService(ProductRepositoryJpa jpaRepo) {
		return new ReadProductService(jpaRepo);
	}
	
	@Bean
	public UpdateProductService updateProductService(ProductRepositoryJpa jpaRepo) {
		return new UpdateProductService(jpaRepo);
	}
	
	@Bean
	public DeleteProductService deleteProductService(ProductRepositoryJpa jpaRepo) {
		return new DeleteProductService(jpaRepo);
	}
}

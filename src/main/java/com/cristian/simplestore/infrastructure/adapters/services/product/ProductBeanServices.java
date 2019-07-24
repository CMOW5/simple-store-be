package com.cristian.simplestore.infrastructure.adapters.services.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cristian.simplestore.domain.services.product.CreateProductService;
import com.cristian.simplestore.domain.services.product.DeleteProductService;
import com.cristian.simplestore.domain.services.product.ReadProductService;
import com.cristian.simplestore.domain.services.product.UpdateProductService;
import com.cristian.simplestore.infrastructure.adapters.repository.product.ProductRepositoryJpa;
import com.cristian.simplestore.infrastructure.adapters.repository.product.ProductRepositoryJpaInterface;

@Configuration
public class ProductBeanServices {
	@Bean
	public CreateProductService createProductService(ProductRepositoryJpaInterface jpaRepo) {
		return new CreateProductService(new ProductRepositoryJpa(jpaRepo));
	}
	
	@Bean
	public ReadProductService readProductService(ProductRepositoryJpaInterface jpaRepo) {
		return new ReadProductService(new ProductRepositoryJpa(jpaRepo));
	}
	
	@Bean
	public UpdateProductService updateProductService(ProductRepositoryJpaInterface jpaRepo) {
		return new UpdateProductService(new ProductRepositoryJpa(jpaRepo));
	}
	
	@Bean
	public DeleteProductService deleteProductService(ProductRepositoryJpaInterface jpaRepo) {
		return new DeleteProductService(new ProductRepositoryJpa(jpaRepo));
	}
}

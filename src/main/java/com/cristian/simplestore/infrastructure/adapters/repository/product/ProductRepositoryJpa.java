package com.cristian.simplestore.infrastructure.adapters.repository.product;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.infrastructure.adapters.pagination.SpringPaginated;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ProductEntity;

@Repository
public class ProductRepositoryJpa implements ProductRepository {

	private final ProductRepositoryJpaInterface jpaRepo;

	@Autowired
	public ProductRepositoryJpa(ProductRepositoryJpaInterface jpaRepo) {
		this.jpaRepo = jpaRepo;
	}
	
	@Override
	public Optional<Product> find(Product product) {
		ProductEntity foundProduct = jpaRepo.findByName(product.getName());
		return Optional.ofNullable(ProductEntity.toDomain(foundProduct));
	}
	
	@Override
	public Optional<Product> findById(Long id) {
		Optional<ProductEntity> entity = jpaRepo.findById(id);
		return entity.isPresent() ? Optional.of(ProductEntity.toDomain(entity.get())) : Optional.empty() ;
	}
	
	@Override
	public List<Product> findAll() {
		List<ProductEntity> entities = jpaRepo.findAll();
		return ProductEntity.toDomain(entities);
	}
	
	@Override
	public Paginated<Product> findAll(int page, int size) {
		Page<ProductEntity> paginated = jpaRepo.findAll(PageRequest.of(page, size));
		List<Product> products = ProductEntity.toDomain(paginated.getContent());
		return SpringPaginated.of(paginated, products);
	}
	
	@Override
	public boolean exists(Product product) {
		return find(product).isPresent();
	}
	
	@Override
	public boolean existsByName(String name) {
		return jpaRepo.existsByName(name);
	}

	@Override
	public Product save(Product product) {
		ProductEntity entity = ProductEntity.fromDomain(product);
		ProductEntity savedEntity = jpaRepo.save(entity);
		return ProductEntity.toDomain(savedEntity);
	}

	@Override
	public void delete(Product product) {
		jpaRepo.delete(ProductEntity.fromDomain(product));
	}

	@Override
	public void deleteById(Long id) {
		jpaRepo.deleteById(id);
	}
}

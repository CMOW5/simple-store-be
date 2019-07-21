package com.cristian.simplestore.infrastructure.adapters.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;
import com.cristian.simplestore.infrastructure.adapters.repository.entities.ProductEntity;

@Component
public class ProductRepositoryJpa implements ProductRepository {

  private final ProductRepositoryJpaInterface jpaRepo;
  
  @Autowired
  public ProductRepositoryJpa(ProductRepositoryJpaInterface jpaRepo) {
    this.jpaRepo = jpaRepo;
  }
  
  @Override
  public Product save(Product product) {
    ProductEntity entity = ProductEntity.fromDomain(product);
    ProductEntity savedEntity = jpaRepo.save(entity);
    return ProductEntity.toDomain(savedEntity);
  }

  @Override
  public boolean exists(Product product) {
    return find(product).isPresent(); 
  }

  @Override
  public Optional<Product> find(Product product) {
    ProductEntity foundProduct = jpaRepo.findByName(product.getName());
    return Optional.ofNullable(ProductEntity.toDomain(foundProduct));
  }

  @Override
  public List<Product> findAll() {
    List<ProductEntity> entities = jpaRepo.findAll();
    return ProductEntity.toDomain(entities);
  }

  @Override
  public void delete(Product product) {
    jpaRepo.delete(ProductEntity.fromDomain(product));
  }

  @Override
  public Product update(Product product) {
    ProductEntity storedProduct = jpaRepo.findByName(product.getName());
    
    String name = product.getName();
    String description = product.getDescription();
    double price = product.getPrice();
    double priceSale = product.getPriceSale();
    boolean inSale = product.isInSale();
    boolean active = product.isActive();
    // Category category = null;
    // entity.category = CategoryEntity.fromDomain(product.getCategory());
    long stock = product.getStock();
    // entity.images = 
    // List<Image> images = new ArrayList<>();
    
    
    storedProduct.setName(name);
    storedProduct.setDescription(description);
    storedProduct.setPrice(price);
    storedProduct.setPriceSale(priceSale);
    storedProduct.setInSale(inSale);
    storedProduct.setActive(active);
    // category
    storedProduct.setStock(stock);
    // images
    
    ProductEntity updatedEntity = jpaRepo.save(storedProduct);
    return ProductEntity.toDomain(updatedEntity);
  }

}

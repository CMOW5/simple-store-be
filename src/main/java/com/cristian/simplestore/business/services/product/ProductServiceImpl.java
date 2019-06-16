package com.cristian.simplestore.business.services.product;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.business.services.image.ImageService;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;


@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ImageService imageService;

  private static final String PRODUCT_NOT_FOUND_EXCEPTION =
      "The product with the given id was not found";

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
    this.productRepository = productRepository;
    this.imageService = imageService;
  }

  public List<ProductEntity> findAll() {
    List<ProductEntity> productEntities = new ArrayList<>();
    productRepository.findAll().forEach(productEntities::add);
    return productEntities;
  }

  @Override
  public Page<ProductEntity> findAll(int page, int size) {
    return productRepository.findAll(PageRequest.of(page, size));
  }

  public ProductEntity findById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));
  }

  @Transactional
  public ProductEntity create(ProductEntity productEntity) {
    return productRepository.save(productEntity);
  }

  @Transactional
  public ProductEntity create(ProductCreateRequest form) {
    ProductEntity productEntity = new ProductEntity();

    productEntity.setName(form.getName());
    productEntity.setDescription(form.getDescription());
    productEntity.setPrice(form.getPrice());
    productEntity.setPriceSale(form.getPriceSale());
    productEntity.setInSale(form.isInSale());
    productEntity.setActive(form.isActive());
    productEntity.setCategory(form.getCategory());
    productEntity.setStock(form.getStock());
    productEntity = addImagesToProduct(productEntity, form.getImages());

    return productRepository.save(productEntity);
  }

  @Transactional
  public ProductEntity update(ProductUpdateRequest form) {
    ProductEntity storedProduct = productRepository.findById(form.getId())
        .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));
    List<Long> imagesIdsToDelete = form.getImagesIdsToDelete();
    List<MultipartFile> newImages = form.getNewImages();

    storedProduct.setName(form.getName());
    storedProduct.setDescription(form.getDescription());
    storedProduct.setPrice(form.getPrice());
    storedProduct.setPriceSale(form.getPriceSale());
    storedProduct.setInSale(form.isInSale());
    storedProduct.setActive(form.isActive());
    storedProduct.setCategory(form.getCategory());
    storedProduct.setStock(form.getStock());
    storedProduct = addImagesToProduct(storedProduct, newImages);
    storedProduct = deleteProductImages(storedProduct, imagesIdsToDelete);

    return storedProduct;
  }

  public ProductEntity addImagesToProduct(ProductEntity productEntity, List<MultipartFile> newImages) {
    List<ImageEntity> images = imageService.saveAll(newImages);
    productEntity.addImages(images);
    return productEntity;
  }

  public ProductEntity deleteProductImages(ProductEntity productEntity, List<Long> imagesIdsToDelete) {
    List<ImageEntity> imagesToDelete = imageService.findAllById(imagesIdsToDelete);
    productEntity.removeImages(imagesToDelete);
    imageService.deleteAll(imagesToDelete);
    return productEntity;
  }

  public void deleteById(Long id) {
    try {
      productRepository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION);
    }
  }

  public Page<ProductEntity> test() {
    Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(0, 20));
    return productEntities;
  }

  public long count() {
    return productRepository.count();
  }
}

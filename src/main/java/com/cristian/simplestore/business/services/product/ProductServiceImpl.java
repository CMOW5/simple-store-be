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
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;


@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private ImageService imageService;
  
  private static final String PRODUCT_NOT_FOUND_EXCEPTION = "The product with the given id was not found";


  public List<Product> findAll() {
    List<Product> products = new ArrayList<>();
    productRepository.findAll().forEach(products::add);
    return products;
  }
  
  @Override
  public Page<Product> findAll(int page, int size) {
    return productRepository.findAll(PageRequest.of(page, size));
  }

  public Product findById(Long id) {
    return productRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));
  }

  @Transactional
  public Product create(Product product) {
    return productRepository.save(product);
  }

  @Transactional
  public Product create(ProductCreateRequest form) {
    Product product = new Product();

    product.setName(form.getName());
    product.setDescription(form.getDescription());
    product.setPrice(form.getPrice());
    product.setPriceSale(form.getPriceSale());
    product.setInSale(form.isInSale());
    product.setActive(form.isActive());
    product.setCategory(form.getCategory());
    product.setStock(form.getStock());
    product = addImagesToProduct(product, form.getImages());

    return productRepository.save(product);
  }

  @Transactional
  public Product update(ProductUpdateRequest form) {
    Product storedProduct = productRepository.findById(form.getId()).orElseThrow(
        () -> new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));
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

  public Product addImagesToProduct(Product product, List<MultipartFile> newImages) {
    List<Image> images = imageService.saveAll(newImages);
    product.addImages(images);
    return product;
  }

  public Product deleteProductImages(Product product, List<Long> imagesIdsToDelete) {
    List<Image> imagesToDelete = imageService.findAllById(imagesIdsToDelete);
    product.removeImages(imagesToDelete);
    imageService.deleteAll(imagesToDelete);
    return product;
  }

  public void deleteById(Long id) {
    try {
      productRepository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new EntityNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION);
    }
  }

  public Page<Product> test() {
    Page<Product> products = productRepository.findAll(PageRequest.of(0, 20));
    return products;
  }

  public long count() {
    return productRepository.count();
  }
}

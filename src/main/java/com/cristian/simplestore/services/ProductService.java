package com.cristian.simplestore.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Image;
import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.forms.ProductCreateForm;
import com.cristian.simplestore.forms.ProductUpdateForm;
import com.cristian.simplestore.respositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private ImageService imageService;
	
	public List<Product> findAllProducts() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
	
	public Product findById(long id) {
		return productRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Product create(Product product) {
		return productRepository.save(product);
	}
	
	@Transactional
	public Product create(ProductCreateForm form) {
		Product product = new Product();
		
		product.setName(form.getName());
		product.setDescription(form.getDescription());
		product.setPrice(form.getPrice());
		product.setPriceSale(form.getPriceSale());
		product.setInSale(form.isInSale());
		product.setActive(form.isActive());
		product.setCategory(form.getCategory());
		product.setUnits(form.getUnits());
		
		List<Image> images = this.imageService.saveAll(form.getImages());
		product.addImages(images);
		
		return productRepository.save(product);
	}
	
	public Product update(long id, Product product) {
		product.setId(id);
		return productRepository.save(product);
	}
	
	@Transactional
	public Product update(ProductUpdateForm form) {
		Product storedProduct = productRepository.findById(form.getId()).orElse(null);
		List<Long> imagesIdsToDelete = form.getImagesIdsToDelete();
		List<MultipartFile> newImages = form.getNewImages();
		
		storedProduct.setName(form.getName());
		storedProduct.setDescription(form.getDescription());
		storedProduct.setPrice(form.getPrice());
		storedProduct.setPriceSale(form.getPriceSale());
		storedProduct.setInSale(form.isInSale());
		storedProduct.setActive(form.isActive());
		storedProduct.setCategory(form.getCategory());
		storedProduct.setUnits(form.getUnits());
		
		List<Image> images = this.imageService.saveAll(newImages);
		storedProduct.addImages(images);
		
		
		Iterable<Image> imagesToDelete = imageService.findAllById(imagesIdsToDelete);
		storedProduct.removeImages(imagesToDelete);
	
		return storedProduct;
	}
	
	public void deleteById(long id) {
		productRepository.deleteById(id);
	}
	
	public Page<Product>  test() {
		Page<Product> products = productRepository.findAll(new PageRequest(0,20));
		return products;
	}
	
	public long count() {
		 return this.productRepository.count();
	}

}

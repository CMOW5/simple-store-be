package com.cristian.simplestore.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;


@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private ImageService imageService;
	
	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}
	
	public Product findById(long id) {
		try {
			Product foundProduct = productRepository.findById(id).get();
			return foundProduct;
		} catch (NoSuchElementException exception) {
			throw new EntityNotFoundException("The product with the given id was not found");
		}
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
		try {
			Product storedProduct = productRepository.findById(form.getId()).get();
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
		} catch (NoSuchElementException exception) {
			throw new EntityNotFoundException("The product with the given id was not found");
		}
	}
	
	public void deleteById(long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The category with the given id was not found");
		}
	}
	
	public Page<Product>  test() {
		Page<Product> products = productRepository.findAll(PageRequest.of(0,20));
		return products;
	}
	
	public long count() {
		 return this.productRepository.count();
	}

}

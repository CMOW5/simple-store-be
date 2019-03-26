package com.cristian.simplestore.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.business.services.storage.ImageStorageService;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.ImageRepository;


@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	public Image findById(Long id) {
		try {
			return this.imageRepository.findById(id).get();
		} catch (NoSuchElementException exception) {
			throw new EntityNotFoundException("The image with the given id was not found");
		}
	}
	
	public List<Image> findAllById(List<Long> imagesIds) {
		Iterable<Image> storedImages = this.imageRepository.findAllById(imagesIds);
		
		List<Image> images = new ArrayList<>();
		storedImages.forEach(images::add);
		
		return images;
	}
	
	@Transactional
	public Image save(MultipartFile file) {	
		String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
		Image image = new Image();
		image.setName(imageNameWithPath);
		return this.imageRepository.save(image);
	}
	
	public Image save(Image image) {
		return this.imageRepository.save(image);
	}
	
	public List<Image> saveAll(List<MultipartFile> imagesFiles) {
		List<Image> savedImages = new ArrayList<Image>();
		
		for (MultipartFile imageFile : imagesFiles) {
			Image image = save(imageFile);
			savedImages.add(image);
		}
		return savedImages;
	}
	
	public void delete(Image image) {
		try {
			this.imageRepository.delete(image);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The image with the given id was not found");
		} catch (IllegalArgumentException exception) {
			// return
		}
	}
	
	public void deleteById(Long id) {
		try {
			this.imageRepository.deleteById(id);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The image with the given id was not found");
		}
	}
	
	public void deleteAllById(Iterable<Long> imagesIdsToDelete) {
		Iterable<Image> imagesToDelete = this.imageRepository.findAllById(imagesIdsToDelete);
		this.imageRepository.deleteAll(imagesToDelete);
	}
		
	public void deleteAll(Iterable<Image> imagesToDelete) {
		this.imageRepository.deleteAll(imagesToDelete);
	}
	
	private String generateImageName(MultipartFile file) {
		String fileName = 
				UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		return fileName;
	}
}

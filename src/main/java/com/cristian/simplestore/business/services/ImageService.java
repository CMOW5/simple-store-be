package com.cristian.simplestore.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.ImageRepository;


@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	@Transactional
	public Image save(MultipartFile file) {	
		String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
		Image image = new Image();
		image.setName(imageNameWithPath);
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
	
	public Image save(Image image) {
		return this.imageRepository.save(image);
	}
	
	public Iterable<Image> findAllById(List<Long> imagesIds) {
		return this.imageRepository.findAllById(imagesIds);
	}
	
	public void deleteImagesById(List<Long> imagesIdsToDelete) {
		Iterable<Image> imagesToDelete = 
				this.imageRepository.findAllById(imagesIdsToDelete);
		
		this.imageRepository.deleteAll(imagesToDelete);
	}
	
	public void delete(Image image) {
		if (image == null) return;
		this.imageRepository.delete(image);
	}
	
	public void deleteById(Long id) {
		this.imageRepository.deleteById(id);
	}
	
	public void deleteAll(Iterable<Image> imagesToDelete) {
		this.imageRepository.deleteAll(imagesToDelete);
	}
	
	public void deleteAllById(Iterable<Long> imagesIdsToDelete) {
		Iterable<Image> imagesToDelete = this.imageRepository.findAllById(imagesIdsToDelete);
		this.imageRepository.deleteAll(imagesToDelete);
	}
		
	private String generateImageName(MultipartFile file) {
		String fileName = 
				UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		return fileName;
	}
}

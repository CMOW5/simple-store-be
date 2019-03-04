package com.cristian.simplestore.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Image;
import com.cristian.simplestore.respositories.ImageRepository;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	public Image save(MultipartFile file) {
		Image i = new Image();
		Image image = this.imageRepository.save(i); // exception
		String imageName = imageStorageService.store(file, generateImageName(image, file));
		image.setName(imageName);
		return this.imageRepository.save(image);
	}
	
	public List<Image> saveAll(List<MultipartFile> imagesFiles) {
		List<Image> savedImages = new ArrayList<Image>();
		
		for (MultipartFile imageFile : imagesFiles) {
			Image image = this.imageRepository.save(new Image());
			String imageName = 
					imageStorageService.store(imageFile, 
							generateImageName(image, imageFile));
			image.setName(imageName);
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
	
	public void deleteById(Long id) {
		this.imageRepository.deleteById(id);
	}
	
	/**
	 * generate the image name, the name will be the stored image id + the file extension
	 * example: image stored id = 50; file extension = .png; the name will be
	 * 			50.png 
	 * @param image the image repository
	 * @param the image file
	 * @return
	 */
	private String generateImageName(Image image, MultipartFile file) {
		return String.valueOf(image.getId()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
	}
}

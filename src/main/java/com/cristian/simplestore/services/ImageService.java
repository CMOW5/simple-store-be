package com.cristian.simplestore.services;

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
	
	public Image createImageRepoFromFile(MultipartFile file) {
		Image image = this.imageRepository.save(new Image());
		String imageName = imageStorageService.store(file, generateImageName(image, file));
		image.setName(imageName);
		return this.imageRepository.save(image);
	}
	
	public Image save(Image image) {
		return this.imageRepository.save(image);
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

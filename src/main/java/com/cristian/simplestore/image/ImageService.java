package com.cristian.simplestore.image;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.storage.ImageStorageService;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	public Image createImageRepoFromFile(MultipartFile file) {
		Image image = this.imageRepository.save(new Image());
		String imageName 
			= String.valueOf(image.getId()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		imageName = imageStorageService.store(file, imageName);
		image.setName(imageName);
		return this.imageRepository.save(image);
	}
	
	public Image save(Image image) {
		return this.imageRepository.save(image);
	}
}

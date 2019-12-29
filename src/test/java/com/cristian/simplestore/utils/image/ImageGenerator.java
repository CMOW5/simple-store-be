package com.cristian.simplestore.utils.image;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.image.repository.ImageRepository;

@Component
public class ImageGenerator {

	@Autowired
	private final ImageFactory imageFactory;

	@Autowired 
	private final ImageRepository imageRepository;
	
	public ImageGenerator(ImageRepository imageRepository, ImageFactory imageFactory) {
		this.imageRepository = imageRepository;
		this.imageFactory = imageFactory;
	}

	public Image saveRandomImageOnDb() {
		MultipartFile imageFile = MockImageFileFactory.createMockMultiPartFile();
		return this.imageRepository.save(imageFactory.fromFile(imageFile));
	}

	public List<Image> saveRandomImagesOnDb(long numberOfImages) {
		List<Image> savedImages = new ArrayList<>();

		for (int i = 0; i < numberOfImages; i++) {
			savedImages.add(this.saveRandomImageOnDb());
		}

		return savedImages;
	}
}

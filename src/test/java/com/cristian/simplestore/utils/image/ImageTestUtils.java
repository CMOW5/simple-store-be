package com.cristian.simplestore.utils.image;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.application.image.ImageFactory;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.image.repository.ImageRepository;

@Component
public class ImageTestUtils {

	@Autowired
	ImageFactory imageFactory;

	@Autowired
	ImageFileFactory imageFileFactory;
	
	@Autowired 
	ImageRepository imageRepository;

	public Image saveRandomImageOnDb() {
		MultipartFile imageFile = MockImageFileFactory.createMockMultiPartFile();
		return this.imageRepository.save(imageFactory.create(imageFile));
	}

	public List<Image> saveRandomImagesOnDb(long numberOfImages) {
		List<Image> savedImages = new ArrayList<>();

		for (int i = 0; i < numberOfImages; i++) {
			savedImages.add(this.saveRandomImageOnDb());
		}

		return savedImages;
	}

	public MultipartFile generateMockMultipartFile() {
		return MockImageFileFactory.createMockMultiPartFile();
	}

	public List<MultipartFile> generateMockMultiPartFiles(long numberOfImages) {
		List<MultipartFile> files = new ArrayList<>();

		for (int i = 0; i < numberOfImages; i++) {
			files.add(generateMockMultipartFile());
		}

		return files;
	}

	public Resource storeImageOnDisk() {
		return imageFileFactory.storeImageOnDisk();
	}

	public Resource storeImageOnDisk(String filename) {
		return imageFileFactory.storeImageOnDisk(filename);
	}

	public List<Resource> storeImagesOnDisk(int size) {
		return imageFileFactory.storeImagesOnDisk(size);
	}
}
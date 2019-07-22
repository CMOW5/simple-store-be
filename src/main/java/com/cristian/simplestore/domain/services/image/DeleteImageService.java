package com.cristian.simplestore.domain.services.image;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.ports.repository.ImageRepository;

public class DeleteImageService {

	private final ImageRepository imageRepository;
	// private final StorageService imageStorageService;

	public DeleteImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
		//this.imageStorageService = storageService;
	}

	public void execute(Image image) {
		if (image == null) return;
		try {
			imageRepository.delete(image);
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException("The image with the given id was not found");
		} catch (IllegalArgumentException exception) {
			// return
		}
	}
}

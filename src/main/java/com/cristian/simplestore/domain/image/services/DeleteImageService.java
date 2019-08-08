package com.cristian.simplestore.domain.image.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;

import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.image.repository.ImageRepository;

public class DeleteImageService {

	private final ImageRepository imageRepository;

	public DeleteImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
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

package com.cristian.simplestore.persistence.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.cristian.simplestore.persistence.entities.Image;

public interface ImageRepository {

	Optional<Image> findById(Long id);// extends CrudRepository<Image, Long> {

	Image save(Image image);

	void delete(Image image);

	void deleteById(Long id);

	void deleteAll(Iterable<Image> imagesToDelete);

	Iterable<Image> findAllById(Iterable<Long> imagesIdsToDelete);

}

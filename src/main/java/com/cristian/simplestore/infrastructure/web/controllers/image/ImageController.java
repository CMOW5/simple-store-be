package com.cristian.simplestore.infrastructure.web.controllers.image;

import java.io.IOException;
import java.io.InputStream;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.infrastructure.storage.ImageStorageService;
import com.cristian.simplestore.infrastructure.storage.exception.StorageFileNotFoundException;
import com.cristian.simplestore.infrastructure.web.controllers.response.ApiResponse;

@RestController
public class ImageController {

	@Autowired
	ImageStorageService imageStorageService;

	@GetMapping(value = "api/images/{filename:..+}")
	public ResponseEntity<?> serveImage(@PathVariable String filename) throws IOException {
		Resource file;
		try {
			file = imageStorageService.loadAsResource(filename);
			InputStream fileStream = file.getInputStream();
			return new ResponseEntity<>(IOUtils.toByteArray(fileStream), HttpStatus.OK);
		} catch (StorageFileNotFoundException e) {
			throw new EntityNotFoundException(e.getMessage());
		}

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// TODO: why is this not working ?? status code = 500 is returned instead
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception) {
		return new ApiResponse().status(HttpStatus.NOT_FOUND).content(null).build();
	}
}

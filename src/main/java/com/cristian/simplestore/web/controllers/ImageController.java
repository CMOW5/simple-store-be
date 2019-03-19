package com.cristian.simplestore.web.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.business.services.ImageStorageService;
import com.cristian.simplestore.business.services.storage.exceptions.StorageFileNotFoundException;

@RestController
public class ImageController {
	
	@Autowired ImageStorageService imageStorageService;
	
	@GetMapping(
			value = "api/images/{filename:..+}"
	)
	public @ResponseBody byte[] serveImage(@PathVariable String filename) throws IOException {
		Resource file;
		try {
			file = imageStorageService.loadAsResource(filename);
	        InputStream fileStream = file.getInputStream();
	        return IOUtils.toByteArray(fileStream);
		} 
		catch (StorageFileNotFoundException e) {
			return null;
		}
		 
    }
}

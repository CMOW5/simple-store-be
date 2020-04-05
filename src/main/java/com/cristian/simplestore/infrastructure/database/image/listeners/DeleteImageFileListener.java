package com.cristian.simplestore.infrastructure.database.image.listeners;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.infrastructure.database.image.ImageEntity;
import com.cristian.simplestore.infrastructure.storage.ImageStorageService;

@Component
public class DeleteImageFileListener {
	
	@Autowired 
	ImageStorageService imageStorageService;
	
	@PostRemove
	public void removeImage(ImageEntity image) {
		imageStorageService.delete(image.getFileName());
	}

}

package com.cristian.simplestore.testcontrollers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.storage.ImageStorageService;
import com.cristian.simplestore.storage.StorageService;


@RestController
public class TestController {
	
	private final ImageStorageService storageService;
	
	@Autowired
    public TestController(ImageStorageService storageService) {
        this.storageService = storageService;
    }
	
	@GetMapping(
			value = "/files/{filename:.+}",
			produces = MediaType.IMAGE_JPEG_VALUE
			)
    public @ResponseBody byte[] serveFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        
  
        InputStream is = null;
        is = file.getInputStream();
      
        return IOUtils.toByteArray(is);
        
        // return is; 
        /*
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                */
    }
	
	/*
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    */
}

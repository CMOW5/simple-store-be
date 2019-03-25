package com.cristian.simplestore.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.business.services.storage.StorageConfig;

@Component
public class ImageBuilder {
		
	private Path defaultRootPath;
	
	private String defaultExtension = ".jpg";
	
	@Autowired
	public ImageBuilder(StorageConfig storageConfig) {
		this.defaultRootPath = Paths.get(storageConfig.getLocation());
	}
	
	public MultipartFile createMultipartImage() throws IOException {
		Resource resource = this.createImage();
		byte[] fileBytes = readFileToByteArray(resource.getFile());
		String imageName = generateRandomName();
		
		MultipartFile result = new MockMultipartFile(imageName, imageName, 
				MediaType.IMAGE_PNG_VALUE, fileBytes);
		
		return result;
	}
	
	public Resource createImage() throws IOException {
		return createImage(this.defaultRootPath.toString(), this.generateRandomImageName());
	}
	
	public Resource createImage(String filename) throws IOException {
		return createImage(this.defaultRootPath.toString(), filename);
	}
	
	public Resource createImage(String path, String imageName) throws IOException {
		Path tempFile = Files.createFile(Paths.get(path + "/" + this.generateRandomImageName()));
		String fullPath = path + "/" + imageName;
		File imageFile = createImageFile(fullPath);
		byte[] imageBytes = readFileToByteArray(imageFile);
		Files.write(tempFile, imageBytes);
		return new FileSystemResource(tempFile.toFile());
	}
	
	public File createImageFile(String imagePath) {
		if (imagePath.isEmpty()) {
			imagePath = this.defaultRootPath.toString() + 
					"/" + this.generateRandomImageName();
		}
		
		int width = 250;
		int height = 250;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();

		// fill all the image with white
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);

		// create a circle with black
		g2d.setColor(Color.black);
		g2d.fillOval(0, 0, width, height);

		// create a string with yellow
		g2d.setColor(Color.yellow);
		g2d.drawString("Test Image", 50, 120);

		// Disposes of this graphics context and releases any system resources that it
		// is using.
		g2d.dispose();

		// Save as JPG
		File file = new File(imagePath);
		try {
			ImageIO.write(bufferedImage, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}
	
	private static byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		// Creating a byte array using the length of the file
		// file.length returns long which is cast to int
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}
	
	public String generateRandomImageName() {
		return this.generateRandomName() + this.defaultExtension;
	}
	
	private String generateRandomName() {
		return UUID.randomUUID().toString();
	}

}

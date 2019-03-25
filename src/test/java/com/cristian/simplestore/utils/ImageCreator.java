package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.business.services.storage.StorageConfig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import javax.imageio.ImageIO;

@Deprecated 
@Component
public class ImageCreator {
	
	private static String DEFAULT_TEMP_FILE_NAME = "test-file";
	private static String DEFAULT_IMAGE_NAME = "test-image";
	private static String DEFAULT_EXTENSION = ".jpg";
	private static String DEFAULT_PATH = "upload-dir"; 
	
	@Autowired
	static StorageConfig storageConfig;
	
	public static Resource createTestImage() throws IOException {
		return createTestImage(DEFAULT_PATH, DEFAULT_IMAGE_NAME);
	}
	
	public static Resource createTestImage(String path, String imageName) throws IOException {
		Path testFile = Files.createTempFile(DEFAULT_TEMP_FILE_NAME, DEFAULT_EXTENSION);
		File imageFile = createImage(path + "/" + imageName);
		byte[] imageBytes = readFileToByteArray(imageFile);
		Files.write(testFile, imageBytes);
		return new FileSystemResource(testFile.toFile());
	}

	public static File createImage(String imagePath) {
		if (imagePath.isEmpty()) {
			imagePath = DEFAULT_PATH + "/" + DEFAULT_IMAGE_NAME;
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
	
	public static MultipartFile createMultipartImage() {
		byte[] content = ImageCreator.createImageByte("");
		String name = DEFAULT_TEMP_FILE_NAME;
		String originalFileName = DEFAULT_TEMP_FILE_NAME;
		
		MultipartFile result = new MockMultipartFile(name,
                originalFileName, MediaType.IMAGE_PNG_VALUE, content);
		return result;
	}
	
	public static byte[] createImageByte(String imagePath) {
		File file = createImage(imagePath);
		return readFileToByteArray(file);
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
}

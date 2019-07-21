package com.cristian.simplestore.business.services.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.business.services.storage.exceptions.StorageException;
import com.cristian.simplestore.business.services.storage.exceptions.StorageFileNotFoundException;
import com.cristian.simplestore.config.properties.StorageConfig;


// @Service
public class ImageStorageService implements StorageService {

  private final Path rootLocation;

  @Autowired
  public ImageStorageService(StorageConfig properties) {
    rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public String store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    return store(file, filename);
  }

  /**
   * this method store a image in the storage folder with the given filename, the method returns the
   * full path of the image stored
   * 
   * example: /path_directory/filename.extension
   * 
   * @param file
   * @param filename
   * @return
   */
  public String store(MultipartFile file, String filename) {
    try {
      Path fullPath = rootLocation.resolve(filename);

      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new StorageException(
            "Cannot store file with relative path outside current directory " + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }

      return fullPath.toString();
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation))
          .map(rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException("Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  public void delete(String filename) {
    Path fileToDeletePath = Paths.get(rootLocation.toString() + "/" + filename);
    try {
      Files.delete(fileToDeletePath);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
  }

  @Override
  public void deleteAll() {
    try {
      FileUtils.cleanDirectory(rootLocation.toFile());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}

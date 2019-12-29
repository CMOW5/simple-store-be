package com.cristian.simplestore.infrastructure.storage.exception;

public class StorageFileNotFoundException extends StorageException {

  /**
   * 
   */
  private static final long serialVersionUID = -2726625437971726902L;

  public StorageFileNotFoundException(String message) {
    super(message);
  }

  public StorageFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}

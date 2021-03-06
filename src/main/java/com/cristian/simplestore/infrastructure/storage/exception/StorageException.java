package com.cristian.simplestore.infrastructure.storage.exception;

public class StorageException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -1433346998415613370L;

  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}

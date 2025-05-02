package org.ademun.timetableapi.exception;

public class ResourceIsBeingUsedException extends RuntimeException {

  public ResourceIsBeingUsedException(String message) {
    super(message);
  }
}

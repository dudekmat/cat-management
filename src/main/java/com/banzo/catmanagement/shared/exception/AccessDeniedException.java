package com.banzo.catmanagement.shared.exception;

public class AccessDeniedException extends RuntimeException {

  public AccessDeniedException(String message) {
    super(message);
  }
}

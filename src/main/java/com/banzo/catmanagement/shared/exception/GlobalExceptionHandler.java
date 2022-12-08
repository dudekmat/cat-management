package com.banzo.catmanagement.shared.exception;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String VALIDATION_ERROR_ALERT = "alert_validation_error";

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(RecordNotFoundException exception) {

    log.warn("RecordNotFoundException: {}", exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.fromMessage(exception.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleException(BadRequestException exception) {

    log.warn("BadRequestException: {}" + exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.fromMessage(exception.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exception) {

    log.warn("ConstraintViolationException: {}", exception.getMessage());

    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(VALIDATION_ERROR_ALERT)
            .addErrors(
                exception.getConstraintViolations().stream()
                    .map(
                        constraintViolation ->
                            ValidationError.builder()
                                .object(constraintViolation.getPropertyPath().toString())
                                .validationCode(constraintViolation.getMessage())
                                .build())
                    .collect(toList()))
            .build();

    return status(BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(BindException.class)
  ResponseEntity<ErrorResponse> handleException(BindException exception) {

    log.warn("BindException: {}", exception.getMessage());

    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .message(VALIDATION_ERROR_ALERT)
            .addErrors(
                exception.getBindingResult().getFieldErrors().stream()
                    .map(
                        fieldError ->
                            ValidationError.builder()
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .validationCode(fieldError.getCode())
                                .build())
                    .collect(toList()))
            .addErrors(
                exception.getBindingResult().getGlobalErrors().stream()
                    .map(
                        objectError ->
                            ValidationError.builder()
                                .object(objectError.getObjectName())
                                .validationCode(objectError.getCode())
                                .build())
                    .collect(toList()))
            .build();

    return status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleException(AccessDeniedException exception) {

    log.error("AccessDeniedException: " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.fromMessage(exception.getMessage()));
  }

  @ExceptionHandler(TokenException.class)
  public ResponseEntity<ErrorResponse> handleException(TokenException exception) {

    log.error("TokenException: " + exception.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.fromMessage(exception.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception exception) {

    log.error("Unknown error:" + exception.getMessage());
    exception.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.fromMessage(exception.getMessage()));
  }
}

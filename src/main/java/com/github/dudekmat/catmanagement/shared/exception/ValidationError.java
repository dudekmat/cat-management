package com.github.dudekmat.catmanagement.shared.exception;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ValidationError {

  String object;
  String field;
  Object rejectedValue;
  String message;
  String validationCode;
}

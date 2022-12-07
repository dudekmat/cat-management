package com.banzo.catmanagement.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRequest {

  @NotBlank String username;
  @NotBlank String password;
}
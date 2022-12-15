package com.github.dudekmat.catmanagement.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegistrationRequest {

  @NotBlank String username;
  @NotBlank String password;
}

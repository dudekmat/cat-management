package com.github.dudekmat.catmanagement.auth.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtResponse {

  String token;
  Long userId;
  String username;
}

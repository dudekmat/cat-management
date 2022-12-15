package com.github.dudekmat.catmanagement.auth.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {

  private Long id;
  private String username;
  private Set<RoleDetails> roles;
}

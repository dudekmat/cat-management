package com.banzo.catmanagement.auth.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDetails {

  private Long userId;
  private String userName;
  private Set<String> roles;
  private Set<String> permissions;
}

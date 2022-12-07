package com.banzo.catmanagement.auth.service;

import com.banzo.catmanagement.auth.dto.JwtResponse;
import com.banzo.catmanagement.auth.dto.UserDetails;
import javax.servlet.http.HttpServletRequest;

public interface AuthService {

  void resetFailedLogins();

  JwtResponse login(String username, String password);

  JwtResponse register(String username, String password);

  UserDetails currentUser(HttpServletRequest request);
}

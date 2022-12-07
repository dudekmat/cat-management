package com.banzo.catmanagement.auth.controller;

import com.banzo.catmanagement.auth.dto.JwtResponse;
import com.banzo.catmanagement.auth.dto.LoginRequest;
import com.banzo.catmanagement.auth.dto.RegistrationRequest;
import com.banzo.catmanagement.auth.dto.UserDetails;
import com.banzo.catmanagement.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(description = "Send user login request")
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    JwtResponse jwtResponse =
        authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
  }

  @Operation(description = "Send user registration request")
  @PostMapping("/register")
  public ResponseEntity<JwtResponse> register(
      @RequestBody @Valid RegistrationRequest registrationRequest) {
    JwtResponse jwtResponse =
        authService.register(registrationRequest.getUsername(), registrationRequest.getPassword());
    return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
  }

  @Operation(description = "Return current user data")
  @GetMapping("/user")
  public ResponseEntity<UserDetails> currentUser(HttpServletRequest request) {
    return new ResponseEntity<>(authService.currentUser(request), HttpStatus.OK);
  }
}

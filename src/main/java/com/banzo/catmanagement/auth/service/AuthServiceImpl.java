package com.banzo.catmanagement.auth.service;

import com.banzo.catmanagement.auth.dto.JwtResponse;
import com.banzo.catmanagement.auth.dto.UserDetails;
import com.banzo.catmanagement.auth.jwt.JwtTokenProvider;
import com.banzo.catmanagement.auth.mappers.UserMapper;
import com.banzo.catmanagement.auth.model.Role;
import com.banzo.catmanagement.auth.model.User;
import com.banzo.catmanagement.auth.repository.RoleRepository;
import com.banzo.catmanagement.auth.repository.UserRepository;
import com.banzo.catmanagement.shared.exception.AccessDeniedException;
import com.banzo.catmanagement.shared.exception.BadRequestException;
import com.banzo.catmanagement.shared.exception.RecordNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private static final String BAD_CREDENTIALS = "Invalid credentials";
  private static final String DEFAULT_ROLE = "ROLE_VIEWER";

  @Override
  public JwtResponse login(String username, String password) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      return userRepository
          .findByUsername(username)
          .map(
              user -> {
                String token = jwtTokenProvider.generateToken(authentication);
                return JwtResponse.builder()
                    .token(token)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .build();
              })
          .orElseThrow(() -> new AccessDeniedException(BAD_CREDENTIALS));

    } catch (AuthenticationException e) {
      throw new AccessDeniedException(BAD_CREDENTIALS);
    }
  }

  @Transactional
  @Override
  public JwtResponse register(String username, String password) {
    if (userRepository.findByUsername(username).isEmpty()) {

      String encodedPassword = passwordEncoder.encode(password);
      Role defaultRole =
          roleRepository
              .findByName(DEFAULT_ROLE)
              .orElseThrow(
                  () -> new RecordNotFoundException("Role not found, role name: " + DEFAULT_ROLE));

      User user =
          userRepository.save(
              User.builder()
                  .username(username)
                  .password(encodedPassword)
                  .roles(Collections.singleton(defaultRole))
                  .build());

      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));

      String token = jwtTokenProvider.generateToken(authentication);
      return JwtResponse.builder()
          .token(token)
          .userId(user.getId())
          .username(user.getUsername())
          .build();
    } else {
      throw new BadRequestException("Username already in use");
    }
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails currentUser(HttpServletRequest request) {
    return userMapper.toUserDetails(
        userRepository
            .findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)))
            .orElseThrow(() -> new BadRequestException("Invalid user data")));
  }

  @Scheduled(fixedRate = 60000)
  @Transactional
  @Override
  public void resetFailedLogins() {

    log.info("Checking for locked accounts");

    userRepository.findAll().stream()
        .filter(user -> !user.getEnabled() && user.getFailedLoginAttempts() > 0)
        .peek(
            user -> {
              log.info("Resetting failed attempts for user: " + user.getUsername());
              user.setFailedLoginAttempts(0);
              user.setEnabled(true);
            })
        .forEach(userRepository::save);
  }
}

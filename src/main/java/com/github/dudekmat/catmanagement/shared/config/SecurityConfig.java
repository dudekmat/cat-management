package com.github.dudekmat.catmanagement.shared.config;

import com.github.dudekmat.catmanagement.shared.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final JwtTokenFilter jwtTokenFilter;

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
      PasswordEncoder passwordEncoder) throws Exception {
    return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder)
        .and()
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf().disable()
        .authorizeHttpRequests()
        .antMatchers("/actuator/**")
        .permitAll()
        .antMatchers("/api/auth/login")
        .permitAll()
        .antMatchers("/api/auth/register")
        .permitAll()
        .antMatchers("/api/auth/user")
        .permitAll()
        .antMatchers("/h2-console/**")
        .permitAll()
        .antMatchers("/v3/api-docs/**")
        .permitAll()
        .antMatchers("/swagger-ui/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}

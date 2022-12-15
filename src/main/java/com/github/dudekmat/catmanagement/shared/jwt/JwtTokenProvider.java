package com.github.dudekmat.catmanagement.shared.jwt;

import com.github.dudekmat.catmanagement.shared.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  @Value("${auth.jwt.secret-key:}")
  private String secretKey;

  @Value("${auth.jwt.expiration-ms:3600000}")
  private Long validityInMs;

  private final UserDetailsService userDetailsService;

  public static final String INVALID_TOKEN_MESSAGE = "Invalid or expired JWT token";

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String generateToken(Authentication authentication) {

    Claims claims = Jwts.claims().setSubject(authentication.getName());
    claims.put(
        "auth",
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(",")));

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(authentication.getName())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + validityInMs))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw new TokenException(INVALID_TOKEN_MESSAGE);
    }
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
      throw new TokenException(INVALID_TOKEN_MESSAGE);
    }
  }
}

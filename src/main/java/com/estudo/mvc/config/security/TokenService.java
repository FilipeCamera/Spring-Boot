package com.estudo.mvc.config.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.estudo.mvc.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
  
  @Value("${mvc.jwt.expiration}")
  private String expiration;

  public String generateToken(Authentication authentication) {
    Date now = new Date();
    Date expiresIn = new Date(now.getTime() + Long.parseLong(expiration));
    
    User logged = (User) authentication.getPrincipal();

    return Jwts.builder().setIssuer("mvc").setSubject(logged.getId().toString()).setIssuedAt(now).setExpiration(expiresIn).signWith(SignatureAlgorithm.HS256, "teste").compact();
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parser().setSigningKey("teste").parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public UUID getUserId(String token) {
    Claims claims = Jwts.parser().setSigningKey("teste").parseClaimsJws(token).getBody();

    return UUID.fromString(claims.getSubject());
  }
}

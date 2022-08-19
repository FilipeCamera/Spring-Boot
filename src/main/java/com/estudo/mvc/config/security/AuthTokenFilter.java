package com.estudo.mvc.config.security;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.estudo.mvc.models.User;
import com.estudo.mvc.service.UserService;

public class AuthTokenFilter extends OncePerRequestFilter {

 
  private final TokenService tokenService;

  private final UserService userService;

  public AuthTokenFilter(TokenService tokenService, UserService userService) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
  
    String token = recoverToken(request);

    boolean valid = tokenService.isValidToken(token);

    if(valid) {
      authenticate(token);
    }

    filterChain.doFilter(request, response);
  }
  
  private void authenticate(String token) {
    UUID userId = tokenService.getUserId(token);

    User user = userService.findById(userId).get();

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String recoverToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7, token.length());
  }
}

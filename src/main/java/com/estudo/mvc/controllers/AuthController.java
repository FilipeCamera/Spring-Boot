package com.estudo.mvc.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.mvc.config.security.TokenService;
import com.estudo.mvc.controllers.dto.AuthDTO;
import com.estudo.mvc.controllers.dto.TokenDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO authDTO) {
    UsernamePasswordAuthenticationToken login = authDTO.convert();
    try {
      Authentication authentication = authManager.authenticate(login);

      String token = tokenService.generateToken(authentication);

      return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}

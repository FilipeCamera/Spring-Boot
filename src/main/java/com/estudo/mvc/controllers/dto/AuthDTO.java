package com.estudo.mvc.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthDTO {

  @NotBlank
  @NotEmpty
  @Email
  private String email;

  @NotBlank
  @NotEmpty
  @Size(min = 6)
  private String password;

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UsernamePasswordAuthenticationToken convert() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}

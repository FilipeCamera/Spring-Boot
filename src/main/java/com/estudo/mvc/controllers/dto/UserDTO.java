package com.estudo.mvc.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

  @NotBlank
  @NotEmpty
  @NotNull
  private String nickname;

  @NotBlank
  @NotEmpty
  @NotNull
  @Email
  private String email;

  @NotBlank
  @NotNull
  @Size(min = 6)
  private String password;

  @NotBlank
  @NotEmpty
  @NotNull
  @Size(max = 80)
  private String fullname;


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }
}

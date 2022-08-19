package com.estudo.mvc.controllers.dto;

import java.util.UUID;

public class UserResponseDTO {
  private UUID id;
  private String nickname;
  private String fullname;

  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getFullname() {
    return fullname;
  }
  public void setFullname(String fullname) {
    this.fullname = fullname;
  }
  
  
}

package com.estudo.mvc.controllers.dto;

import java.util.UUID;

public class MessageResponseDTO {
  
  private UUID id;
  private String title;
  private String description;

  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
}

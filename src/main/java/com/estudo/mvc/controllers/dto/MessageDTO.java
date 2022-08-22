package com.estudo.mvc.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageDTO {

  @NotBlank
  @NotEmpty
  @NotNull
  private String title;

  @NotBlank
  @NotEmpty
  @NotNull
  private String description;

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

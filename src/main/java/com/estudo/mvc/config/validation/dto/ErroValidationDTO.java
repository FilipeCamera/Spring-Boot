package com.estudo.mvc.config.validation.dto;

public class ErroValidationDTO {
  private String field;
  private String message;

  public ErroValidationDTO(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public String getMessage() {
    return message;
  }
  
}

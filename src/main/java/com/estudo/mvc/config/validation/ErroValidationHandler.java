package com.estudo.mvc.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.estudo.mvc.config.validation.dto.ErroValidationDTO;

@RestControllerAdvice
public class ErroValidationHandler {
  
  @Autowired
  MessageSource messageSource;

  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<ErroValidationDTO> handle(MethodArgumentNotValidException exception) {
    List<ErroValidationDTO> errors = new ArrayList<>();

    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    fieldErrors.forEach(error -> {
      String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      ErroValidationDTO erro = new ErroValidationDTO(error.getField(), message);

      errors.add(erro);
    });

    return errors;
  }
}

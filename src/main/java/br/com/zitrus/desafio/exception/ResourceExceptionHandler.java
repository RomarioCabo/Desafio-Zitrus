package br.com.zitrus.desafio.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<StandardError> InternalServerErrorException(
      InternalServerErrorException e, HttpServletRequest request) {
    StandardError err =
        new StandardError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<StandardError> BadRequestException(
      BadRequestException e, HttpServletRequest request) {
    StandardError err =
        new StandardError(
            HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<StandardError> NotFoundException(
      NotFoundException e, HttpServletRequest request) {
    StandardError err =
        new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }
}

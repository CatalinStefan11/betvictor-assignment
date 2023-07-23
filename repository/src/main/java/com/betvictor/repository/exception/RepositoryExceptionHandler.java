package com.betvictor.repository.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class RepositoryExceptionHandler {

  private static String ERROR_MESSAGE = "Something went wrong";

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleValidationException(Throwable ex, HttpServletRequest request) {
    log.error("Exception occurred with message {}", ex.getMessage(), ex);
    return createHttpErrorInfo(
        HttpStatus.INTERNAL_SERVER_ERROR, 
        request.getRequestURI(),
        Collections.singletonList(ERROR_MESSAGE)
    );
  }

  private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, String path, List<String> message) {
    log.info("Returning HTTP status: {} for path: {}, messages: {}", httpStatus, path, message);
    return new HttpErrorInfo(httpStatus, path, message);
  }
}

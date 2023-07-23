package com.betvictor.processor.exception;

import com.betvictor.processor.util.Constants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.betvictor.processor.util.Constants.FAILED_VALIDATION_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class ProcessorExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(BAD_REQUEST)
  public HttpErrorInfo handleValidationException(ConstraintViolationException ex, HttpServletRequest request) {
    log.error("Exception occurred with message {}", ex.getMessage(), ex);
    return createHttpErrorInfo(HttpStatus.BAD_REQUEST, request.getRequestURI(), extractValidationErrors(ex));
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleThrowable(Throwable ex, HttpServletRequest request) {
    log.error("Exception occurred with message {}", ex.getMessage(), ex);
    return createHttpErrorInfo(
        HttpStatus.INTERNAL_SERVER_ERROR,
        request.getRequestURI(),
        Collections.singletonList(Constants.ERROR_MESSAGE)
    );
  }
  
  private List<String> extractValidationErrors(ConstraintViolationException ex) {
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    List<String> validationMessages = violations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());
    return validationMessages.isEmpty() ? Collections.singletonList(FAILED_VALIDATION_MESSAGE) : validationMessages;
  }
  
  private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, String path, List<String> message) {
    log.info("Returning HTTP status: {} for path: {}, messages: {}", httpStatus, path, message);
    return new HttpErrorInfo(httpStatus, path, message);
  }
}

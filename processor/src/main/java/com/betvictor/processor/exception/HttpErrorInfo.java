package com.betvictor.processor.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

public class HttpErrorInfo {
  private final ZonedDateTime timestamp;
  private final String path;
  private final HttpStatus httpStatus;
  private final List<String> messages;

  public HttpErrorInfo(HttpStatus httpStatus, String path, List<String> messages) {
    this.timestamp = ZonedDateTime.now();
    this.httpStatus = httpStatus;
    this.path = path;
    this.messages = messages;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public String getPath() {
    return path;
  }

  public int getStatus() {
    return httpStatus.value();
  }

  public List<String> getMessages() {
    return messages;
  }
}

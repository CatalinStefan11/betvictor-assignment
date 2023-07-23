package com.betvictor.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages = "com.betvictor.processor.client")
@SpringBootApplication
public class ProcessorAppMain {

  public static void main(final String[] args) {
    SpringApplication.run(ProcessorAppMain.class, args);
  }
  
}

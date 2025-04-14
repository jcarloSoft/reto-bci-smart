package com.bci.smart.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bci.smart.com"})
public class RetoBciSmartApplication {
  public static void main(String[] args) {
    SpringApplication.run(RetoBciSmartApplication.class, args);
  }
}
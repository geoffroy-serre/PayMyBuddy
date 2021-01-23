package com.paymybuddy.app;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class AppApplication {

  public static void main(String[] args) {
    SpringApplication.run(AppApplication.class, args);
  }

}

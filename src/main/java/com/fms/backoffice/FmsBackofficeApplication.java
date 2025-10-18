package com.fms.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FmsBackofficeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FmsBackofficeApplication.class, args);
    }
}

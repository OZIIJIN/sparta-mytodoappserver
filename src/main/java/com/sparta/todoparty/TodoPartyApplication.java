package com.sparta.todoparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodoPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoPartyApplication.class, args);
    }

}

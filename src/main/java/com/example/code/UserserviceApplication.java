package com.example.code;

import com.example.code.domain.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
        System.out.println(Role.ROLE_ADMIN.name());
    }

}

package com.resource.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ResourceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceAdminApplication.class, args);
    }

}

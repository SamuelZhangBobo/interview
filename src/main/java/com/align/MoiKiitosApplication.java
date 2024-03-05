package com.align;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.align")
public class MoiKiitosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoiKiitosApplication.class, args);
    }
}

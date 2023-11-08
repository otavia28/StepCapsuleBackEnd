package com.otavia.stepcapsule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.otavia.stepcapsule.mapper")
public class StepCapsuleBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(StepCapsuleBackEndApplication.class, args);
    }

}

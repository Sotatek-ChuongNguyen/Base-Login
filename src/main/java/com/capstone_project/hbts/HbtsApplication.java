package com.capstone_project.hbts;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@SpringBootApplication
@EnableScheduling
public class HbtsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HbtsApplication.class, args);
    }

}
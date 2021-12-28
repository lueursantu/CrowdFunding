package com.santu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Santu
 * @date 2021/12/23 15:45
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class MainClassAuth {
    public static void main(String[] args) {
        SpringApplication.run(MainClassAuth.class, args);
    }
}

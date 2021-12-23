package com.santu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Santu
 * @date 2021/12/17 16:15
 */

@EnableEurekaServer
@SpringBootApplication
public class MainClassEureka {
    public static void main(String[] args) {
        SpringApplication.run(MainClassEureka.class, args);
    }
}

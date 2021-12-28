package com.snatu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Santu
 * @date 2021/12/23 16:23
 */
@SpringBootApplication
@EnableZuulProxy
public class MainClassZuul {
    public static void main(String[] args) {
        SpringApplication.run(MainClassZuul.class, args);
    }
}

package com.santu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Santu
 * @date 2021/12/20 15:05
 */
@MapperScan("com.santu.crowd.mapper")
@SpringBootApplication
public class MainClassMySQL {
    public static void main(String[] args) {
        SpringApplication.run(MainClassMySQL.class, args);
    }
}

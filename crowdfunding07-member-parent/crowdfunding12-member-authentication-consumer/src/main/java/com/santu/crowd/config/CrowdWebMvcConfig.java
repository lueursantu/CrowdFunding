package com.santu.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Santu
 * @date 2021/12/24 16:58
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 注册页面
        registry.addViewController("/auth/member/to/reg/page.html").setViewName("member-reg");

        // 登录页面
        registry.addViewController("/auth/member/to/login/page.html").setViewName("member-login");

    }
}

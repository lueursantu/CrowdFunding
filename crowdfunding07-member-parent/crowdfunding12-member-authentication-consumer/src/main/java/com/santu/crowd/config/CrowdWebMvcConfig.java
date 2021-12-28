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
        String urlPath = "/auth/member/to/reg/page.html";
        String viewName = "member-reg";

        registry.addViewController(urlPath).setViewName(viewName);
    }
}

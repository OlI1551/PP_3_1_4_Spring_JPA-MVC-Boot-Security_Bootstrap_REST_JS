package ru.kata.spring.boot_bootstrap.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/user").setViewName("user_page");
        registry.addViewController("/admin").setViewName("admin_page");

    }
}

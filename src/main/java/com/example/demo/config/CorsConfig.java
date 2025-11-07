package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Browsers implement the Cross-Origin Resource Sharing (CORS) policy to enhance security
 * by controlling which origins (domains) can access resources from a different origin.
 */
@Configuration
public class CorsConfig {

    /**
     * This bean configures global CORS settings for the whole application.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Allow React (localhost:3000) to access the backend
                        .allowedOriginPatterns("http://localhost:5173")
                        // HTTP methods allowed
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Allow any headers in the request
                        .allowedHeaders("*")
                        // Allow credentials such as cookies or Authorization headers
                        .allowCredentials(true);
            }
        };
    }
}

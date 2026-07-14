package com.lfav07.agentscaffold.config;

import com.lfav07.agentscaffold.config.AppProperties.Cors;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final Cors cors;

    public CorsConfig(AppProperties appProperties) {
        this.cors = appProperties.cors() != null ? appProperties.cors() : new Cors("http://localhost:5173");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(cors.allowedOrigins())
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
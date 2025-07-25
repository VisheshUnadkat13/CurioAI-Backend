package com.ai.ai_educator_platform.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${frontend.url}")
       String frontEndUrl;

    @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
//                    .allowedOrigins(frontEndUrl)
                    .allowedOriginPatterns(frontEndUrl)
                    .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(false)
                    .maxAge(3600);
    }
}



package com.app.linkedin.post_service.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}

package com.app.linkedin.user_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic userCreatedTopic()
    {
        return new NewTopic("user-created-topic",3,(short)1);
    }
}

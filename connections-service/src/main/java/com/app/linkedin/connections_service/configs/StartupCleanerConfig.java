package com.app.linkedin.connections_service.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;

@Configuration
public class StartupCleanerConfig {

    @Bean
    CommandLineRunner clearDatabase(Neo4jClient neo4jClient) {
        return args -> {
            neo4jClient.query("MATCH (n) DETACH DELETE n").run();
        };
    }
}

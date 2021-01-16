package com.example.society.config;

import com.example.society.models.Population;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DB {

    @Bean
    public Map<String, Population> populationDB() {
        return new HashMap<>();
    }
}

package com.example.society.config;

import com.example.society.models.Demographic;
import com.example.society.models.Human;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class DB {

    @Bean
    public Set<Human> humansDB() {
        return new HashSet<>();
    }

    @Bean
    public Map<String, Demographic> demographicStatisticPerCountryDB() {
        return new HashMap<>();
    }
}

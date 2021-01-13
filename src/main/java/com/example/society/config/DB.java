package com.example.society.config;

import com.example.society.models.Human;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DB {

    @Bean
    public Set<Human> humansDB() {
        return new HashSet<>();
    }
}

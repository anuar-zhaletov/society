package com.example.society.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class ImmutableDB {
    @Value("classpath:boynames.txt")
    private Resource boynamesResource;

    @Value("classpath:girlnames.txt")
    private Resource girlnamesResource;

    @Bean
    public List<String> boyNamesDB() throws IOException {
        Path path = Paths.get(boynamesResource.getURI());
        return Files.lines(path).collect(Collectors.toList());
    }

    @Bean
    public List<String> girlNamesDB() throws IOException {
        Path path = Paths.get(girlnamesResource.getURI());
        return Files.lines(path).collect(Collectors.toList());
    }

    @Bean
    public Random random() {
        return new Random();
    }
}

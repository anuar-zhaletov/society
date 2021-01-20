package com.example.society.config;

import com.example.society.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class InitialSetup {
    private final PopulationRepository populationRepository;

//    @Scheduled(fixedRate = 5*1000)
//    public void populationLifecycle() {
//        populationRepository.makeHumanityOlderBy1Year();
//        populationRepository.produceChildren();
//        populationRepository.distributePower();
//    }

//    @Scheduled(fixedRate = 1000)
//    public void changeHappiness() {
//        populationRepository.changeHappiness();
//    }
}

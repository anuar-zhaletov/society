package com.example.society.controllers;

import com.example.society.enums.EducationSystem;
import com.example.society.enums.MoralitySystem;
import com.example.society.enums.PoliticalSystem;
import com.example.society.models.Demographic;
import com.example.society.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HumanController {
    private final PopulationRepository populationRepository;
    private final List<String> countriesDB;

    @GetMapping("getDemographicStatistic")
    public Map<String, Demographic> getDemographicStatistic() {
        return populationRepository.getDemographicStatistic();
    }

    @GetMapping("changeEducationSystem/{country}/{educationSystem}")
    public void changeEducationSystem(@PathVariable String country, @PathVariable EducationSystem educationSystem) {
        populationRepository.changeEducationSystem(country, educationSystem);
    }

    @GetMapping("changeMoralitySystem/{country}/{moralitySystem}")
    public void changeMoralitySystem(@PathVariable String country, @PathVariable MoralitySystem moralitySystem) {
        populationRepository.changeMoralitySystem(country, moralitySystem);
    }

    @GetMapping("changePoliticalSystem/{country}/{politicalSystem}")
    public void changePoliticalSystem(@PathVariable String country, @PathVariable PoliticalSystem politicalSystem) {
        populationRepository.changePoliticalSystem(country, politicalSystem);
    }

    @GetMapping("countries")
    public List<String> getCountries() {
        return countriesDB;
    }

    @GetMapping("educationSystems")
    public EducationSystem[] getEducationSystems() {
        return EducationSystem.values();
    }

    @GetMapping("moralitySystems")
    public MoralitySystem[] getMoralitySystems() {
        return MoralitySystem.values();
    }

    @GetMapping("politicalSystems")
    public PoliticalSystem[] getPoliticalSystems() {
        return PoliticalSystem.values();
    }
}

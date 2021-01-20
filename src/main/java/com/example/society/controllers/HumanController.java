package com.example.society.controllers;

import com.example.society.enums.EducationSystem;
import com.example.society.enums.MoralitySystem;
import com.example.society.models.Demographic;
import com.example.society.models.Human;
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

    @GetMapping("getDemographicStatistic")
    public Demographic getDemographicStatistic() {
        return populationRepository.getDemographicStatistic();
    }

    @GetMapping("changeEducationSystem/{educationSystem}")
    public void changeEducationSystem(@PathVariable EducationSystem educationSystem) {
        populationRepository.changeEducationSystem(educationSystem);
    }

    @GetMapping("changeMoralitySystem/{moralitySystem}")
    public void changeMoralitySystem(@PathVariable MoralitySystem moralitySystem) {
        populationRepository.changeMoralitySystem(moralitySystem);
    }

    @GetMapping("educationSystems")
    public EducationSystem[] getEducationSystems() {
        return EducationSystem.values();
    }

    @GetMapping("moralitySystems")
    public MoralitySystem[] getMoralitySystems() {
        return MoralitySystem.values();
    }

    @GetMapping("humanity")
    public List<Human> humanity() {
        return populationRepository.getHumanity();
    }

    @GetMapping("human/{id}")
    public Human human(@PathVariable Long id) {
        return populationRepository.findHumanById(id);
    }
}

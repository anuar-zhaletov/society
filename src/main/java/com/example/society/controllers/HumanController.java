package com.example.society.controllers;

import com.example.society.models.Human;
import com.example.society.services.HumanGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HumanController {
    private final HumanGeneratorService humanGeneratorService;
    private final Set<Human> humansDB;

    @GetMapping("generatePopulation")
    public void generatePopulation() {
        humanGeneratorService.generatePopulation();
    }

    @GetMapping("getPopulation")
    public Set<Human> getPopulation() {
        return humansDB;
    }
}

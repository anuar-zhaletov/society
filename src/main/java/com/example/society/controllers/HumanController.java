package com.example.society.controllers;

import com.example.society.models.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HumanController {
    private final Set<Human> humansDB;

    @GetMapping("getPopulation")
    public Set<Human> getPopulation() {
        return humansDB;
    }
}

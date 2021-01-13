package com.example.society.controllers;

import com.example.society.models.Demographic;
import com.example.society.models.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class HumanController {
    private final Set<Human> humansDB;
    private final Map<String, Demographic> demographicStatisticPerCountryDB;

    @GetMapping("getDemographicStatistic")
    public Map<String, Demographic> getDemographicStatistic() {
        return demographicStatisticPerCountryDB;
    }
}

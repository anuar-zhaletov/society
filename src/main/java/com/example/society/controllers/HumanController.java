package com.example.society.controllers;

import com.example.society.services.HumanGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("human")
@RequiredArgsConstructor
public class HumanController {
    private final HumanGeneratorService humanGeneratorService;

    @GetMapping("boyname")
    public String getBoyName() {
        return humanGeneratorService.getBoyName();
    }

    @GetMapping("girlname")
    public String getGirlName() {
        return humanGeneratorService.getGirlName();
    }
}

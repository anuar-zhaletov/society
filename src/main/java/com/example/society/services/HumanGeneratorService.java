package com.example.society.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class HumanGeneratorService {
    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;

    public String getBoyName() {
        return boyNamesDB.get(random.nextInt(boyNamesDB.size()));
    }

    public String getGirlName() {
        return girlNamesDB.get(random.nextInt(girlNamesDB.size()));
    }
}

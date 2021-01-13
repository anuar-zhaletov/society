package com.example.society.services;

import com.example.society.enums.SexEnum;
import com.example.society.models.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class HumanGeneratorService {
    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;
    private final List<String> countriesDB;
    private final Set<Human> humansDB;

    public void generatePopulation() {
        IntStream.range(1, 5001).forEach(i -> humansDB.add(generateHuman()));
    }

    public Human generateHuman() {
        SexEnum sex = SexEnum.getAny();
        return Human.builder()
                .name(sex == SexEnum.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(generateAge())
                .country(generateCountry())
                .build();
    }

    private String generateBoyName() {
        return boyNamesDB.get(random.nextInt(boyNamesDB.size()));
    }

    private String generateGirlName() {
        return girlNamesDB.get(random.nextInt(girlNamesDB.size()));
    }

    private String generateCountry() {
        return countriesDB.get(random.nextInt(countriesDB.size()));
    }

    private int generateAge() {
        return random.nextInt(60);
    }
}

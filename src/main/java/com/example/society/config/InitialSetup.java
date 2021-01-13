package com.example.society.config;

import com.example.society.enums.SexEnum;
import com.example.society.models.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class InitialSetup {
    private final int INITIAL_POPULATION_NUMBER = 20000;
    private final int CHILDREN_BORN_EVERY_YEAR = 500;
    private static final int MAX_AGE = 70;

    private final Set<Human> humansDB;
    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;
    private final List<String> countriesDB;

    @Bean
    public void populateWithInitialHumanity() {
        IntStream.range(1, INITIAL_POPULATION_NUMBER + 1)
                .forEach(i -> humansDB.add(generateHuman()));
    }

    @Scheduled(fixedRate = 60*1000)
    public void populationLifecycle() {
        makeHumanityOlderBy1Year();
        produceChildren();
    }

    private void makeHumanityOlderBy1Year() {
        Set<Human> humansToRemove = new HashSet<>();

        humansDB.forEach(human -> {
            if (human.getAge() == MAX_AGE) {
                humansToRemove.add(human);
            } else {
                human.setAge(human.getAge() + 1);
            }
        });

        humansDB.removeAll(humansToRemove);
    }

    private void produceChildren() {
        IntStream.range(1, CHILDREN_BORN_EVERY_YEAR + 1)
                .forEach(i -> humansDB.add(generateChild()));
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

    public Human generateChild() {
        SexEnum sex = SexEnum.getAny();
        return Human.builder()
                .name(sex == SexEnum.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(0)
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
        return random.nextInt(MAX_AGE);
    }
}

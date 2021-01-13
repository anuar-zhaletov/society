package com.example.society.config;

import com.example.society.enums.SexEnum;
import com.example.society.models.Demographic;
import com.example.society.models.Human;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class InitialSetup {
    private final int INITIAL_POPULATION_NUMBER = 100000;
    private final int CHILDREN_BORN_EVERY_YEAR = 2000;
    private static final int MAX_AGE = 70;
    private static final int MAX_EDUCATION_LEVEL = 50;
    private static final int MAX_POWER = 100;
    private static final int MAX_HAPPINESS = 10;

    private final Set<Human> humansDB;
    private final Map<String, Demographic> demographicStatisticPerCountryDB;
    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;
    private final List<String> countriesDB;

    @Bean
    public void populateWithInitialHumanity() {
        IntStream.range(1, INITIAL_POPULATION_NUMBER + 1)
                .forEach(i -> humansDB.add(generateHuman()));
    }

    @Scheduled(fixedRate = 10*1000)
    public void populationLifecycle() {
        makeHumanityOlderBy1Year();
        produceChildren();
        calculateDemographicStatistic();
    }

    private void calculateDemographicStatistic() {
        demographicStatisticPerCountryDB.clear();
        Map<String, Set<Human>> humansGroupedByCountry = humansDB.stream()
                .collect(Collectors.groupingBy(Human::getCountry, Collectors.toSet()));

        humansGroupedByCountry.entrySet().forEach(entry -> {
            Set<Human> humans = entry.getValue();
            long totalPopulation = humans.size();

            long numberOfMan = humans.stream().filter(human -> human.getSex() == SexEnum.MAN).count();
            long manPercentage = (long) ((double) numberOfMan/totalPopulation * 100);
            long womanPercentage = 100 - manPercentage;

            long numberOfChildren = humans.stream().filter(human -> human.getAge() < 18).count();
            long numberOfAdult = totalPopulation - numberOfChildren;

            demographicStatisticPerCountryDB.put(entry.getKey(), Demographic.builder()
                    .totalPopulation(totalPopulation)
                    .manPercentage(manPercentage)
                    .womanPercentage(womanPercentage)
                    .numberOfChildren(numberOfChildren)
                    .numberOfAdult(numberOfAdult)
                    .build());
        });
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
        int age = generateAge();
        return Human.builder()
                .name(sex == SexEnum.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(age)
                .country(generateCountry())
                .morality(generateMorality())
                .education(generateEducation())
                .power(age < 18 ? 0 : 1)
                .happiness(generateHappiness())
                .build();
    }

    public Human generateChild() {
        SexEnum sex = SexEnum.getAny();
        int age = generateAge();
        return Human.builder()
                .name(sex == SexEnum.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(age)
                .country(generateCountry())
                .morality(generateMorality())
                .education(generateEducation())
                .power(0)
                .happiness(generateHappiness())
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

    // Morality in range (-10, 10)
    private int generateMorality() { return random.nextInt(20) - 10; }

    private int generateEducation() { return random.nextInt(MAX_EDUCATION_LEVEL) + 1; }

    private int generateAge() {
        return random.nextInt(MAX_AGE);
    }

    private int generateHappiness() {
        return random.nextInt(MAX_HAPPINESS) + 1;
    }
}

package com.example.society.repository;

import com.example.society.PowerDistributionService;
import com.example.society.enums.EducationSystem;
import com.example.society.enums.MoralitySystem;
import com.example.society.enums.Personality;
import com.example.society.enums.Sex;
import com.example.society.models.Demographic;
import com.example.society.models.Human;
import com.example.society.models.Population;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Repository
public class PopulationRepository {
    private static final int ADULT_AGE = 18;
    private static final AtomicLong HUMAN_ID = new AtomicLong(0);
    private static final int MAX_AGE = 70;
    private static final int MAX_HAPPINESS = 1000;

    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;
    private final PowerDistributionService powerDistributionService;
    private final Population population;

    public PopulationRepository(Random random, List<String> boyNamesDB, List<String> girlNamesDB, PowerDistributionService powerDistributionService) {
        this.random = random;
        this.boyNamesDB = boyNamesDB;
        this.girlNamesDB = girlNamesDB;
        this.powerDistributionService = powerDistributionService;
        this.population = Population.builder()
                .humanity(IntStream.range(1, 2000 + 1).boxed().map(i -> generateInitialHuman()).collect(Collectors.toList()))
                .educationSystem(EducationSystem.getAny())
                .moralitySystem(MoralitySystem.getAny())
                .born(0L)
                .died(0L)
                .killed(0L)
                .build();
    }

    public synchronized void produceChildren() {
        long numberOfChildrenBorn = population.getHumanity().size() / 70;
        LongStream.range(1, numberOfChildrenBorn + 1).forEach(i -> {
            population.getHumanity().add(generateChild(population.getEducationSystem(), population.getMoralitySystem()));
            population.setBorn(population.getBorn() + 1);
        });
    }

    public synchronized void makeHumanityOlderBy1Year() {
        List<Human> humanity = population.getHumanity();
        Set<Human> humanityToRemove = new HashSet<>();
        for (Human human : humanity) {
            if (human.getAge() == MAX_AGE) {
                humanityToRemove.add(human);
            } else {
                human.setAge(human.getAge() + 1);
            }
        }

        humanity.removeAll(humanityToRemove);
        population.setDied(population.getDied() + humanityToRemove.size());
    }

    public synchronized void distributePower() {
        powerDistributionService.distributePower(population);
    }

    public synchronized Demographic getDemographicStatistic() {
        List<Human> humanity = population.getHumanity();
        long totalPopulation = humanity.size();
        long numberOfMan = humanity.stream().filter(human -> human.getSex() == Sex.MAN).count();
        long numberOfWoman = humanity.stream().filter(human -> human.getSex() == Sex.WOMAN).count();
        long manPercentage = (long) ((double) numberOfMan / totalPopulation * 100);
        long womanPercentage = (long) ((double) numberOfWoman / totalPopulation * 100);
        long numberOfChildren = humanity.stream().filter(human -> human.getAge() < ADULT_AGE).count();
        long numberOfAdult = totalPopulation - numberOfChildren;
        long happinessPercentage = (long) ((double) humanity.stream().map(Human::getHappiness).reduce(0L, Long::sum) / (totalPopulation * MAX_HAPPINESS) * 100);
        long educationLevelPercentage = (long) ((double) humanity.stream().map(Human::getEducation).reduce(0L, Long::sum) / (totalPopulation * EducationSystem.LEVEL_5.getMaxLevel()) * 100);
        long moralityPercentage = (long) ((double) humanity.stream().map(Human::getMorality).reduce(0L, Long::sum) / (totalPopulation * MoralitySystem.LEVEL_5.getMaxLevel()) * 100);
        long totalPower = humanity.stream().map(this::getTotalPower).reduce(0L, Long::sum);

        return Demographic.builder()
                .totalPopulation(totalPopulation)
                .manPercentage(manPercentage)
                .womanPercentage(womanPercentage)
                .numberOfChildren(numberOfChildren)
                .numberOfAdult(numberOfAdult)
                .happinessPercentage(happinessPercentage)
                .born(population.getBorn())
                .died(population.getDied())
                .killed(population.getKilled())
                .educationLevelPercentage(educationLevelPercentage)
                .educationSystem(population.getEducationSystem())
                .moralitySystem(population.getMoralitySystem())
                .moralityPercentage(moralityPercentage)
                .totalPower(totalPower)
                .build();
    }

    public synchronized void changeHappiness() {
        long positiveHappiness = 0L;
        long negativeHappiness = 0L;
        List<Human> humanity = population.getHumanity();
        for (Human human : humanity) {
            if (human.getMorality() > 0L) {
                positiveHappiness += getTotalPower(human);
            } else {
                negativeHappiness -= getTotalPower(human);
            }
        }

        while (!humanity.isEmpty() && positiveHappiness > 0L) {
            Human randomHuman = humanity.get(random.nextInt(humanity.size()));
            if (randomHuman.getHappiness() < 10L) {
                randomHuman.setHappiness(randomHuman.getHappiness() + 1);
            } else if (negativeHappiness > 0L) {
                negativeHappiness--;
            }

            positiveHappiness--;
        }

        while (!humanity.isEmpty() && negativeHappiness > 0L) {
            Human randomHuman = humanity.get(random.nextInt(humanity.size()));
            if (randomHuman.getHappiness() <= 1L) {
                humanity.remove(randomHuman);
                population.setKilled(population.getKilled() + 1);
            } else {
                randomHuman.setHappiness(randomHuman.getHappiness() - 1);
            }
            negativeHappiness--;
        }
    }

    public synchronized void changeEducationSystem(EducationSystem educationSystem) {
        population.setEducationSystem(educationSystem);
        population.getHumanity().forEach(human -> human.setEducation(generateEducation(educationSystem)));
    }

    public synchronized void changeMoralitySystem(MoralitySystem moralitySystem) {
        population.setMoralitySystem(moralitySystem);
        population.getHumanity().forEach(human -> human.setMorality(generateMorality(moralitySystem)));
    }

    public synchronized List<Human> getHumanity() {
        return population.getHumanity();
    }

    public synchronized Human findHumanById(Long id) {
        return population.getHumanity()
                .stream().filter(human -> human.getId() == id)
                .findFirst().orElse(null);
    }

    private long getTotalPower(Human human) {
        return human.getMorality() * human.getEducation() * human.getPower();
    }

    private Human generateInitialHuman() {
        Sex sex = Sex.getAny();
        int age = generateAge();
        return Human.builder()
                .id(HUMAN_ID.getAndIncrement())
                .name(sex == Sex.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(age)
                .morality(generateMorality(MoralitySystem.LEVEL_1))
                .education(generateEducation(EducationSystem.LEVEL_1))
                .happiness(generateHappiness())
                .power(1)
                .personality(Personality.getAny())
                .build();
    }

    private Human generateChild(EducationSystem educationSystem, MoralitySystem moralitySystem) {
        Sex sex = Sex.getAny();
        int age = generateAge();
        return Human.builder()
                .id(HUMAN_ID.getAndIncrement())
                .name(sex == Sex.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(age)
                .morality(generateMorality(moralitySystem))
                .education(generateEducation(educationSystem))
                .happiness(generateHappiness())
                .power(0)
                .personality(Personality.getAny())
                .build();
    }

    private String generateBoyName() {
        return boyNamesDB.get(random.nextInt(boyNamesDB.size()));
    }

    private String generateGirlName() {
        return girlNamesDB.get(random.nextInt(girlNamesDB.size()));
    }

    private int generateMorality(MoralitySystem moralitySystem) {
        return random.nextInt(moralitySystem.getMaxLevel() - moralitySystem.getMinLevel()) + moralitySystem.getMinLevel();
    }

    private int generateEducation(EducationSystem educationSystem) {
        return random.nextInt(educationSystem.getMaxLevel()) + 1;
    }

    private int generateAge() {
        return random.nextInt(MAX_AGE);
    }

    private int generateHappiness() {
        return MAX_HAPPINESS;
    }
}

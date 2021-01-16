package com.example.society.repository;

import com.example.society.enums.EducationSystem;
import com.example.society.enums.PoliticalSystem;
import com.example.society.enums.Sex;
import com.example.society.models.Demographic;
import com.example.society.models.Human;
import com.example.society.models.Population;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PopulationRepository {
    private static final int ADULT_AGE = 18;
    private static AtomicLong HUMAN_ID = new AtomicLong(0);
    private static final int MAX_AGE = 70;
    private final int INITIAL_POPULATION_NUMBER = 100000;
    private static final int MAX_POWER = 100;
    private static final int MAX_HAPPINESS = 1000;
    private static final int MAX_MORALITY = 10;

    private final Random random;
    private final List<String> boyNamesDB;
    private final List<String> girlNamesDB;
    private final List<String> countriesDB;

    private final Map<String, Population> populationDB;

    public synchronized void produceInitialHumanity() {
        IntStream.range(1, INITIAL_POPULATION_NUMBER + 1).forEach(i -> {
            putHumanIntoPopulationDB(generateCountry(), generateInitialHuman());
        });
    }

    public synchronized void produceChildren() {
        populationDB.forEach((country, population) -> {
            long numberOfChildrenBorn = population.getHumanity().size() / 35;
            LongStream.range(1, numberOfChildrenBorn + 1).forEach(i -> {
                putHumanIntoPopulationDB(country, generateChild(population.getEducationSystem()));
                population.setBorn(population.getBorn() + 1);
            });
        });
    }

    public synchronized void makeHumanityOlderBy1Year() {
        populationDB.forEach((country, population) -> {
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
        });
    }

    public synchronized void distributePower() {
        populationDB.forEach((country, population) -> {
            population.getHumanity().forEach(human -> {
                if (human.getAge() >= ADULT_AGE) {
                    human.setPower(1);
                }
            });
        });
    }

    public synchronized Map<String, Demographic> getDemographicStatistic() {
        return populationDB.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    List<Human> humanity = e.getValue().getHumanity();
                    long totalPopulation = humanity.size();
                    long numberOfMan = humanity.stream().filter(human -> human.getSex() == Sex.MAN).count();
                    long numberOfWoman = humanity.stream().filter(human -> human.getSex() == Sex.WOMAN).count();
                    long manPercentage = (long) ((double) numberOfMan / totalPopulation * 100);
                    long womanPercentage = (long) ((double) numberOfWoman / totalPopulation * 100);
                    long numberOfChildren = humanity.stream().filter(human -> human.getAge() < ADULT_AGE).count();
                    long numberOfAdult = totalPopulation - numberOfChildren;
                    long happinessPercentage = (long) ((double) humanity.stream().map(Human::getHappiness).reduce(0L, Long::sum) / (totalPopulation * MAX_HAPPINESS) * 100);
                    PoliticalSystem politicalSystem = e.getValue().getPoliticalSystem();
                    long educationLevelPercentage = (long) ((double) humanity.stream().map(Human::getEducation).reduce(0L, Long::sum) / (totalPopulation * EducationSystem.LEVEL_5.getMaxLevel()) * 100);
                    long moralityPercentage = (long) ((double) humanity.stream().map(Human::getMorality).reduce(0L, Long::sum) / (totalPopulation * MAX_MORALITY) * 100);
                    long totalPower = humanity.stream().map(this::getTotalPower).reduce(0L, Long::sum);

                    return Demographic.builder()
                            .totalPopulation(totalPopulation)
                            .manPercentage(manPercentage)
                            .womanPercentage(womanPercentage)
                            .numberOfChildren(numberOfChildren)
                            .numberOfAdult(numberOfAdult)
                            .happinessPercentage(happinessPercentage)
                            .politicalSystem(politicalSystem)
                            .born(e.getValue().getBorn())
                            .died(e.getValue().getDied())
                            .killed(e.getValue().getKilled())
                            .educationLevelPercentage(educationLevelPercentage)
                            .educationSystem(e.getValue().getEducationSystem())
                            .moralityPercentage(moralityPercentage)
                            .totalPower(totalPower)
                            .build();
                }));
    }

    private void putHumanIntoPopulationDB(String country, Human human) {
        if (populationDB.containsKey(country)) {
            populationDB.get(country).getHumanity().add(human);
        } else {
            List<Human> humanity = new ArrayList<>();
            humanity.add(human);
            populationDB.put(country, Population.builder()
                    .humanity(humanity)
                    .politicalSystem(PoliticalSystem.ANARCHY)
                    .educationSystem(EducationSystem.LEVEL_1)
                    .born(0L)
                    .died(0L)
                    .killed(0L)
                    .build()
            );
        }
    }

    public synchronized void changeHappiness() {
        populationDB.forEach((country, population) -> {
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
        });
    }

    public synchronized void changeEducationSystem(String country, EducationSystem educationSystem) {
        Population population = populationDB.get(country);

        if (population != null) {
            population.setEducationSystem(educationSystem);
            population.getHumanity().forEach(human -> human.setEducation(generateEducation(educationSystem)));
        }
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
                .morality(generateMorality())
                .education(generateEducation(EducationSystem.LEVEL_1))
                .power(age < ADULT_AGE ? 0 : 1)
                .happiness(generateHappiness())
                .build();
    }

    private Human generateChild(EducationSystem educationSystem) {
        Sex sex = Sex.getAny();
        int age = generateAge();
        return Human.builder()
                .id(HUMAN_ID.getAndIncrement())
                .name(sex == Sex.MAN ? generateBoyName() : generateGirlName())
                .sex(sex)
                .age(age)
                .morality(generateMorality())
                .education(generateEducation(educationSystem))
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

    private int generateMorality() {
        return random.nextInt(2 * MAX_MORALITY) - MAX_MORALITY;
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

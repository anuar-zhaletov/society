package com.example.society;

import com.example.society.models.Human;
import com.example.society.models.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PowerDistributionService {
    private static final int ADULT_AGE = 18;

    public synchronized void distributePower(Population population) {
        for (Human human : population.getHumanity()) {
            if (human.getAge() >= ADULT_AGE && human.getMorality() > 0) {
                long power = human.getEducation() * human.getMorality();
            }
        }
        // if blockchain power should be spread based on education * morality
    }
}

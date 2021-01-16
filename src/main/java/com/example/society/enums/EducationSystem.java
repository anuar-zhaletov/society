package com.example.society.enums;

import java.util.List;
import java.util.Random;

public enum EducationSystem {
    LEVEL_1(1), LEVEL_2(10), LEVEL_3(50), LEVEL_4(150), LEVEL_5(500);

    private final int maxLevel;

    EducationSystem(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    private static final List<EducationSystem> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static EducationSystem getAny()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

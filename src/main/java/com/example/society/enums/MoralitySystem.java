package com.example.society.enums;

import java.util.List;
import java.util.Random;

public enum MoralitySystem {
    LEVEL_1(-10, 6), LEVEL_2(-7, 7), LEVEL_3(-4, 8), LEVEL_4(-1, 9), LEVEL_5(2, 10);

    private final int minLevel;
    private final int maxLevel;

    MoralitySystem(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    private static final List<MoralitySystem> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static MoralitySystem getAny()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

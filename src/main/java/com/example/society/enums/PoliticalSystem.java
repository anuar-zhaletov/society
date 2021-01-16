package com.example.society.enums;

import java.util.List;
import java.util.Random;

public enum PoliticalSystem {
    ANARCHY, DEMOCRACY, DICTATORSHIP;

    private static final List<PoliticalSystem> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PoliticalSystem getAny()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

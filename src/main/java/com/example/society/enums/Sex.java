package com.example.society.enums;

import java.util.List;
import java.util.Random;

public enum Sex {
    MAN, WOMAN;

    private static final List<Sex> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Sex getAny()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

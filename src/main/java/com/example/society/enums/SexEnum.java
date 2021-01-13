package com.example.society.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum SexEnum {
    MAN, WOMAN;

    private static final List<SexEnum> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static SexEnum getAny()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}

package com.example.society.enums;

public enum EducationSystem {
    LEVEL_1(1), LEVEL_2(10), LEVEL_3(50), LEVEL_4(150), LEVEL_5(500);

    private int maxLevel;

    EducationSystem(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}

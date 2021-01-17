package com.example.society.models;

import com.example.society.enums.EducationSystem;
import com.example.society.enums.MoralitySystem;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Demographic {
    private long totalPopulation;
    private long manPercentage;
    private long womanPercentage;
    private long numberOfChildren;
    private long numberOfAdult;
    private long happinessPercentage;
    private long born;
    private long died;
    private long killed;
    private long educationLevelPercentage;
    private EducationSystem educationSystem;
    private MoralitySystem moralitySystem;
    private long moralityPercentage;
    private long totalPower;
}

package com.example.society.models;

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
}

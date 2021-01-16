package com.example.society.models;

import com.example.society.enums.EducationSystem;
import com.example.society.enums.MoralitySystem;
import com.example.society.enums.PoliticalSystem;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Population {
    private List<Human> humanity;
    private PoliticalSystem politicalSystem;
    private EducationSystem educationSystem;
    private MoralitySystem moralitySystem;
    private long born;
    private long died;
    private long killed;
}

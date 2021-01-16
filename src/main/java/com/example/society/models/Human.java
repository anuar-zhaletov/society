package com.example.society.models;

import com.example.society.enums.Sex;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Human {
    private long id;
    private String name;
    private long age;
    private Sex sex;
    private long morality;
    private long education;
    private long power;
    private long happiness;
}

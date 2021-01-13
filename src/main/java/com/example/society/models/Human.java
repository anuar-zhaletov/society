package com.example.society.models;

import com.example.society.enums.SexEnum;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Human {
    private String name;
    private int age;
    private SexEnum sex;
    private String country;
}

package com.example.society.models;

import com.example.society.enums.SexEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Human {
    private String name;
    private int age;
    private SexEnum sex;
    private String country;
}

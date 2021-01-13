package com.example.society.models;

import com.example.society.enums.SexEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Human {
    String name;
    int age;
    SexEnum sex;
}

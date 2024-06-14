package com.example.Kojiro.entity;

import lombok.Data;

import java.util.List;

@Data
public class TestInput{
        int q_id;
        int user_select;
        int flag;
        List<TestInput> inputList;
}

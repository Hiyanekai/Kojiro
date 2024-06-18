package com.example.Kojiro.entity;

public record question(int id, int genre_id, String sentence, int answer, String explain, String file, int score){}
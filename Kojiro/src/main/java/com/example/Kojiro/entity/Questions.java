package com.example.Kojiro.entity;

public record Questions(int id, int genre_id, String sentence, int answer, String explain, String file, int score) {
}

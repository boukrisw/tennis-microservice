package fr.latelier.tennis.domain;

import lombok.Data;

@Data
public class PlayerData {
    private int rank;
    private int points;
    private double weight;
    private double height;
    private int age;
    private int[] last;
}

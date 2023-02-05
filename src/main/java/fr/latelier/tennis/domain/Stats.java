package fr.latelier.tennis.domain;

import lombok.Data;

@Data
public class Stats {
    private Country country;
    private double imc;
    private double medianHeight;
}

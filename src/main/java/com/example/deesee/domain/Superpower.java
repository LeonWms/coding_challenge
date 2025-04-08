package com.example.deesee.domain;

public enum Superpower {
    STRENGTH("strength"),
    SPEED("speed"),
    FLIGHT("flight"),
    INVULNERABILITY("invulnerability"),
    HEALING("healing");

    private final String value;

    Superpower(String value) {
        this.value = value;
    }

}

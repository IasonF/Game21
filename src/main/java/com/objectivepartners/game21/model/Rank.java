package com.objectivepartners.game21.model;

public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(1),
    QUEEN(2),
    KING(3),
    ACE(11);

    private final int value;

    private Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

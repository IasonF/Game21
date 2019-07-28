package com.objectivepartners.game21.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Hand {
    private List<Rank> cards;
    private int value;

    public Hand(Collection<Rank> cards) {
        this.cards = new ArrayList<>(cards);
        this.value = HandUtil.value(cards);
    }

    public List<Rank> getCards() {
        return cards;
    }


    public int getValue() {
        return value;
    }

}

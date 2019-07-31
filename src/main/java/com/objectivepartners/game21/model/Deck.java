package com.objectivepartners.game21.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public final class Deck implements Serializable {
    LinkedList<Rank> cards;

    public Deck() {
        cards = Collections.nCopies(4, Arrays.asList(Rank.values()))
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(cards);
    }

    public Deck(List cards) {
        this.cards = new LinkedList<>(cards);
    }

    Rank deal() {
        Rank next = cards.poll();
        if (nonNull(next)) {
            return next;
        } else {
            throw new IllegalStateException("The deck is empty!");
        }
    }

}

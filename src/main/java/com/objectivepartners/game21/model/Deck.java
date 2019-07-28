package com.objectivepartners.game21.model;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
final class Deck {
    LinkedList<Rank> cards;

    Deck() {
        cards = Collections.nCopies(4, Arrays.asList(Rank.values()))
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(cards);
    }

    Deck(List cards) {
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

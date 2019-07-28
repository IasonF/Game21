package com.objectivepartners.game21.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;


public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private final Deck deck;
    private final List<Rank> bank = new ArrayList<>();
    private final List<Rank> firstHand = new ArrayList<>();
    private final List<Rank> secondHand = new ArrayList<>();
    private boolean firstHandActive = true;
    private boolean secondHandActive = false;
    private boolean split = false;
    private State state;


    public Game(Deck deck) {
        this.deck = requireNonNull(deck);
        deal(bank);
        deal(firstHand);
        setState(State.FIRST_HAND_PLAYS);
    }

    public GameState currentState() {
        return new GameState(state, new Hand(bank), new Hand(firstHand), new Hand(secondHand));

    }

    public GameState hit() {
        assertUpdatable();
        if (firstHandActive)
            deal(firstHand);
        if (secondHandActive)
            deal(secondHand);
        postUpdate();
        return currentState();
    }

    public GameState stand() {
        if (!split) {
            setState(State.BANK_PLAYS);
        } else {
            if (firstHandActive) {
                firstHandActive = false;
                secondHandActive = true;
                setState(State.SECOND_HAND_PLAYS);
            } else if (secondHandActive) {
                secondHandActive = false;
                setState(State.BANK_PLAYS);
//                playBank();
//                setGameOutcome();
            }
        }
        postUpdate();
        return currentState();
    }

    public GameState split() {
        return update(this::setSplit);
    }

    private GameState update(Runnable updateLogic) {
        assertUpdatable();
        updateLogic.run();
        postUpdate();
        return currentState();
    }

    private void assertUpdatable() {
        if (!(state == State.FIRST_HAND_PLAYS || state == State.SECOND_HAND_PLAYS))
            throw new GameAlreadyCompleteException();
    }


    private void postUpdate() {
        if (state == State.BANK_PLAYS) {
            playBank();
            setGameOutcome();
            return;
        }
        if (!split) {
            if (HandUtil.isBust(firstHand)) {
                setState(State.LOSE);
            } else if (isBanksTurn()) {
                playBank();
                setGameOutcome();
            }
        } else {
            if (firstHandActive) {
                if (HandUtil.isgame21(firstHand) || HandUtil.isBust(firstHand)) {
                    firstHandActive = false;
                    secondHandActive = true;
                    setState(State.SECOND_HAND_PLAYS);
                }
            } else if (secondHandActive)
                if (HandUtil.isgame21(secondHand) || HandUtil.isBust(secondHand)) {
                    secondHandActive = false;
                    setState(State.BANK_PLAYS);
                    playBank();
                    setGameOutcome();
                } else if (state == State.BANK_PLAYS) {
                    playBank();
                    setGameOutcome();
                }
        }
    }

    private boolean isBanksTurn() {
        return state == State.BANK_PLAYS || HandUtil.isTarget(firstHand);
    }

    private void playBank() {
        while (HandUtil.isBelowBankMin(bank)) {
            deal(bank);
        }
    }

    private void setGameOutcome() {
        State gameOutcome = determineOutcome(bank, firstHand, secondHand);
        setState(gameOutcome);
    }

    private void deal(List<Rank> hand) {
        Rank next = requireNonNull(deck.deal());
        LOG.info("The next card is: " + next);
        hand.add(next);
    }

    private void setSplit() {
        if (((firstHand.size() == 2) && (firstHand.get(0) == firstHand.get(1)))) {
            secondHand.add(firstHand.remove(1));
            split = true;
        } else
            throw new IllegalSplitException();
    }

    private void setState(State newState) {
        LOG.info(" changed from " + state + " to " + newState);
        state = newState;
    }

    private State determineOutcome(Collection<Rank> bank, Collection<Rank> first, Collection<Rank> second) {
        int firstHandValue = HandUtil.value(first);
        int secondHandValue = HandUtil.value(second);
        int bankValue = HandUtil.value(bank);
        State result = State.LOSE;

        if (HandUtil.isBust(bankValue)) {
            return State.WIN;
        }
        if (split) {
            int wins = 0;
            if (firstHandValue > bankValue)
                wins++;
            if (secondHandValue > bankValue)
                wins++;
            switch (wins) {
                case 0:
                    result = State.LOSE;
                case 1:
                    result = State.DRAW;
                case 2:
                    result = State.WIN;
            }
        } else {
            result = (firstHandValue > bankValue) ? State.WIN : State.LOSE;
        }
        return result;
    }
}

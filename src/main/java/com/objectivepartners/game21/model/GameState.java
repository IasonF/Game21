package com.objectivepartners.game21.model;

import io.swagger.annotations.ApiModelProperty;

public final class GameState {
    @ApiModelProperty(value = "The current state of this game.", required = true)
    private final State state;
    @ApiModelProperty(value = "The cards currently held by the bank.", required = true)
    private final Hand bank;
    @ApiModelProperty(value = "The first hand.", required = true)
    private final Hand first;
    @ApiModelProperty(value = "The second hand.", required = true)
    private final Hand second;

    public GameState(State state, Hand bank, Hand first, Hand second) {
        this.state = state;
        this.bank = bank;
        this.first = first;
        this.second = second;
    }


    public State getState() {
        return state;
    }

    public Hand getBank() {
        return bank;
    }

    public Hand getFirst() {
        return first;
    }

    public Hand getSecond() {
        return second;
    }
}

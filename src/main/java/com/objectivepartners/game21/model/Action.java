package com.objectivepartners.game21.model;

import java.util.function.Function;

public enum Action {
    HIT(Game::hit),
    STAND(Game::stand),
    SPLIT(Game::split),
    FORCE_SPLIT(Game::forceSplit);

    private final Function<Game, GameState> logic;

    Action(Function<Game, GameState> logic) {
        this.logic = logic;
    }

    public GameState update(Game game) {
        return logic.apply(game);
    }
}

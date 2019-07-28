package com.objectivepartners.game21.model;

import org.springframework.stereotype.Component;

@Component
public class GameHandler {

    private Game currentGame;

    public Game createGame() {
        currentGame = new Game(new Deck());
        return currentGame;
    }

    public Game getGame() {
        return currentGame;
    }
}

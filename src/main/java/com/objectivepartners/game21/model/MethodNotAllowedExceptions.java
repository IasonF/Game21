package com.objectivepartners.game21.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
final class GameAlreadyCompleteException extends RuntimeException {
    public GameAlreadyCompleteException() {
        super("Game is already completed. Please start a new game.");
    }
}

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
final class IllegalSplitException extends RuntimeException {
    public IllegalSplitException() {
        super("You can only split, if the first two cards are the same.");
    }
}

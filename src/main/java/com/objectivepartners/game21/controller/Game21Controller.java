package com.objectivepartners.game21.controller;

import com.objectivepartners.game21.model.Action;
import com.objectivepartners.game21.model.Game;
import com.objectivepartners.game21.model.GameHandler;
import com.objectivepartners.game21.model.GameState;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api("Game 21 Api.")
@RestController
@RequestMapping("/com/objectivepartners/game21")
public final class Game21Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Game21Controller.class);

    @Autowired
    private GameHandler gameHandler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Start a new game of 21.")
    @ApiResponses(@ApiResponse(code = 200, message = "OK"))
    public GameState newGame() {
        Game game = gameHandler.createGame();
        LOG.info("Game has been created.");
        return game.currentState();
    }


    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Check game status.")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public GameState viewGame() {
        Game game = gameHandler.getGame();
        return game.currentState();
    }

    @RequestMapping(path = "/Game21", method = RequestMethod.POST)
    @ApiOperation(value = "Make a move.")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 405, message = "Move is not allowed.")})
    public GameState updateGame(
            @RequestParam("action")
            @ApiParam(value = "HIT for a new card, SPLIT for splitting and STAND for finishing the current hand.", required = true) Action action) {
        Game game = gameHandler.getGame();
        return action.update(game);
    }
}

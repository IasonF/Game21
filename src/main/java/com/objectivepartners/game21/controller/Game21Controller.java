package com.objectivepartners.game21.controller;

import com.objectivepartners.game21.model.Action;
import com.objectivepartners.game21.model.Deck;
import com.objectivepartners.game21.model.Game;
import com.objectivepartners.game21.model.GameState;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Api("Game 21 Api.")
@RestController
@RequestMapping("/com/objectivepartners/game21")
public final class Game21Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Game21Controller.class);

    private static final Path SAVED_GAME = Paths.get(System.getProperty("user.home"), "SavedGame.ser");

    private Game game;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Start a new game of 21.")
    @ApiResponses(@ApiResponse(code = 200, message = "OK"))
    public GameState newGame() {
        game = new Game(new Deck());
        LOG.info("Game has been created.");
        save(game);
        return game.currentState();
    }


    @GetMapping
    @ApiOperation(value = "Check game status.")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public GameState viewGame() {
        if (Objects.nonNull(game))
            return game.currentState();
        else {
            load();
            return game.currentState();
        }
    }

    @PostMapping(path = "/Game21")
    @ApiOperation(value = "Make a move.")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 405, message = "Move is not allowed.")})
    public GameState updateGame(
            @RequestParam("action")
            @ApiParam(value = "HIT for a new card, SPLIT for splitting and STAND for finishing the current hand.", required = true) Action action) {
        action.update(game);
        save(game);
        return game.currentState();
    }

    public void save(Game game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVED_GAME.toString()))) {
            oos.writeObject(game);
        } catch (IOException e) {
            LOG.info("Cannot save game.");
        }
    }

    public void load() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(SAVED_GAME.toString()));) {
            game = (Game) objectInputStream.readObject();
        } catch (IOException e) {
            LOG.info("Cannot load game.");
        } catch (ClassNotFoundException e) {
            LOG.info("Saved game is corrupted.");
        }
    }
}

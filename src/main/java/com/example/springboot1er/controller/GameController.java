package com.example.springboot1er.controller;

import com.example.springboot1er.model.GameCreationParams;
import com.example.springboot1er.service.GameService;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.web.bind.annotation.*;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import com.example.springboot1er.model.MoveParams;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame(@RequestBody GameCreationParams params) {
        return gameService.createGame(params);
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable UUID gameId) {
        return gameService.getGame(gameId);
    }

    @GetMapping("/{gameId}/tokens/{playerId}/moves")
    public Collection<CellPosition> getAvailableMoves(
            @PathVariable UUID gameId,
            @PathVariable UUID playerId) {
        return gameService.getAvailableMoves(gameId, playerId);
    }
    @PostMapping("/{gameId}/moves")
    public Game playMove(
            @PathVariable UUID gameId,
            @RequestBody MoveParams params) {
        return gameService.playMove(gameId, params);
    }

}

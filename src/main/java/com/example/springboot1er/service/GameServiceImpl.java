package com.example.springboot1er.service;

import com.example.springboot1er.model.GameCreationParams;
import com.example.springboot1er.model.MoveParams;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final Map<UUID, Game> games = new HashMap<>();
    private final List<GamePlugin> gamePlugins;

    public GameServiceImpl(List<GamePlugin> gamePlugins) {
        this.gamePlugins = gamePlugins;
    }

    @Override
    public Game createGame(GameCreationParams params) {
        for (GamePlugin plugin : gamePlugins) {
            if (plugin.getGameId().equals(params.getGameType())) {
                Game game = plugin.createGame();
                games.put(game.getId(), game);
                return game;
            }
        }
        throw new IllegalArgumentException("Type de jeu inconnu : " + params.getGameType());
    }

    @Override
    public Game getGame(UUID gameId) {
        return games.get(gameId);
    }

    @Override
    public Collection<CellPosition> getAvailableMoves(UUID gameId, UUID playerId) {
        Game game = games.get(gameId);
        for (Token token : game.getRemainingTokens()) {
            if (token.getOwnerId().isPresent()
                    && token.getOwnerId().get().equals(playerId)
                    && token.canMove()) {
                return token.getAllowedMoves();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Game playMove(UUID gameId, MoveParams params) {
        Game game = games.get(gameId);
        for (Token token : game.getRemainingTokens()) {
            if (token.getOwnerId().isPresent()
                    && token.getOwnerId().get().equals(params.getPlayerId())
                    && token.canMove()) {
                try {
                    token.moveTo(params.getPosition());
                } catch (InvalidPositionException e) {
                    throw new IllegalArgumentException("Position invalide : " + e.getMessage());
                }
                return game;
            }
        }
        throw new IllegalArgumentException("Aucun jeton jouable pour ce joueur");
    }

}
package com.example.springboot1er.service;

import com.example.springboot1er.config.GameConfig;
import com.example.springboot1er.model.GameCreationParams;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import org.springframework.stereotype.Service;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import com.example.springboot1er.model.MoveParams;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final Map<UUID, Game> games = new HashMap<>();
    private final List<GameFactory> gameFactories;

    public GameServiceImpl(List<GameFactory> gameFactories) {
        this.gameFactories = gameFactories;
    }

    @Override
    public Game createGame(GameCreationParams params) {
        for (GameFactory factory : gameFactories) {
            if (factory.getGameFactoryId().equals(params.getGameType())) {
                Game game = factory.createGame(params.getPlayerCount(), params.getBoardSize());
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

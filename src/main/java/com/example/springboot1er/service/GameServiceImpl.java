package com.example.springboot1er.service;

import com.example.springboot1er.config.GameConfig;
import com.example.springboot1er.model.GameCreationParams;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

}

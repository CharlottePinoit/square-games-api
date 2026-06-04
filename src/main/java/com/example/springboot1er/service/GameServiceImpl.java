package com.example.springboot1er.service;

import com.example.springboot1er.DTO.GameCreationParams;
import com.example.springboot1er.DTO.MoveParams;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private final GameDao gameDao;
    private final List<GamePlugin> gamePlugins;

    public GameServiceImpl(GameDao gameDao, List<GamePlugin> gamePlugins) {
        this.gameDao = gameDao;
        this.gamePlugins = gamePlugins;
    }

    @Override
    public Game createGame(UUID userId, GameCreationParams params) {
        for (GamePlugin plugin : gamePlugins) {
            if (plugin.getGameId().equals(params.getGameType())) {
                Set<UUID> players = Set.of(
                        userId,
                        UUID.randomUUID()
                );
                Game game = plugin.createGame(params.getBoardSize(), players);
                return gameDao.upsert(game);
            }
        }
        throw new IllegalArgumentException("Type de jeu inconnu : " + params.getGameType());
    }

    @Override
    public Game getGame(UUID gameId) {
        return gameDao.findById(gameId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Partie introuvable : " + gameId));
    }

    @Override
    public Collection<CellPosition> getAvailableMoves(UUID gameId, UUID playerId) {
        Game game = gameDao.findById(gameId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Partie introuvable : " + gameId));
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
        Game game = gameDao.findById(gameId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Partie introuvable : " + gameId));
        for (Token token : game.getRemainingTokens()) {
            if (token.getOwnerId().isPresent()
                    && token.getOwnerId().get().equals(params.getPlayerId())
                    && token.canMove()) {
                try {
                    token.moveTo(params.getPosition());
                } catch (InvalidPositionException e) {
                    throw new IllegalArgumentException("Position invalide : " + e.getMessage());
                }
                return gameDao.upsert(game);
            }
        }
        throw new IllegalArgumentException("Aucun jeton jouable pour ce joueur");
    }

}
package com.example.springboot1er.service;

import com.example.springboot1er.model.GameEntity;
import com.example.springboot1er.model.GameTokenEntity;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Stream;

@Repository
@Primary
public class JpaGameDao implements GameDao {

    private final GameEntityRepository repository;
    private final List<GamePlugin> gamePlugins;

    public JpaGameDao(GameEntityRepository repository, List<GamePlugin> gamePlugins) {
        this.repository = repository;
        this.gamePlugins = gamePlugins;
    }

    @Override
    public Stream<Game> findAll() {
        return repository.findAll().stream()
                .map(this::toGame);
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return repository.findById(gameId)
                .map(this::toGame);
    }

    @Override
    public Game upsert(Game game) {
        GameEntity entity = toEntity(game);
        repository.save(entity);
        return game;
    }

    @Override
    public void delete(String gameId) {
        repository.deleteById(gameId);
    }

    private GameEntity toEntity(Game game) {
        GameEntity entity = new GameEntity();
        entity.id = game.getId().toString();
        entity.factoryId = game.getFactoryId();
        entity.boardSize = game.getBoardSize();
        entity.playerIds = game.getPlayerIds().stream()
                .map(UUID::toString)
                .reduce("", (a, b) -> a.isEmpty() ? b : a + "," + b);
        entity.tokens = new ArrayList<>();
        for (Token token : game.getRemainingTokens()) {
            entity.tokens.add(toTokenEntity(token, false));
        }
        for (Token token : game.getBoard().values()) {
            entity.tokens.add(toTokenEntity(token, false));
        }
        for (Token token : game.getRemovedTokens()) {
            entity.tokens.add(toTokenEntity(token, true));
        }
        return entity;
    }

    private GameTokenEntity toTokenEntity(Token token, boolean removed) {
        GameTokenEntity tokenEntity = new GameTokenEntity();
        tokenEntity.ownerId = token.getOwnerId().map(UUID::toString).orElse(null);
        tokenEntity.name = token.getName();
        tokenEntity.removed = removed;
        tokenEntity.x = token.getPosition() != null ? token.getPosition().x() : null;
        tokenEntity.y = token.getPosition() != null ? token.getPosition().y() : null;
        return tokenEntity;
    }

    private Game toGame(GameEntity entity) {
        Set<UUID> playerIds = Arrays.stream(entity.playerIds.split(","))
                .map(UUID::fromString)
                .collect(java.util.stream.Collectors.toSet());
        for (GamePlugin plugin : gamePlugins) {
            if (plugin.getGameId().equals(entity.factoryId)) {
                return plugin.createGame(entity.boardSize, playerIds);
            }
        }
        throw new IllegalArgumentException("Factory inconnue : " + entity.factoryId);
    }

}
package com.example.springboot1er.service;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@Primary
public class JdbcGameDao implements GameDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final List<GamePlugin> gamePlugins;

    public JdbcGameDao(NamedParameterJdbcTemplate jdbcTemplate, List<GamePlugin> gamePlugins) {
        this.jdbcTemplate = jdbcTemplate;
        this.gamePlugins = gamePlugins;
    }

    @Override
    public Stream<Game> findAll() {
        String sql = "SELECT * FROM games";
        return jdbcTemplate.query(sql, Map.of(), (rs, rowNum) -> findById(rs.getString("id")).orElseThrow()).stream();
    }

    @Override
    public Optional<Game> findById(String gameId) {
        String sql = "SELECT * FROM games WHERE id = :id";
        List<Game> results = jdbcTemplate.query(sql, Map.of("id", gameId), (rs, rowNum) -> {
            String factoryId = rs.getString("factory_id");
            for (GamePlugin plugin : gamePlugins) {
                if (plugin.getGameId().equals(factoryId)) {
                    return plugin.createGame();
                }
            }
            throw new IllegalArgumentException("Factory inconnue : " + factoryId);
        });
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Game upsert(Game game) {
        String sql = """
                INSERT INTO games (id, factory_id, status)
                VALUES (:id, :factoryId, :status)
                ON CONFLICT (id) DO UPDATE SET status = :status
                """;
        jdbcTemplate.update(sql, Map.of(
                "id", game.getId().toString(),
                "factoryId", game.getFactoryId(),
                "status", game.getStatus().toString()
        ));
        return game;
    }

    @Override
    public void delete(String gameId) {
        String sql = "DELETE FROM games WHERE id = :id";
        jdbcTemplate.update(sql, Map.of("id", gameId));
    }

}
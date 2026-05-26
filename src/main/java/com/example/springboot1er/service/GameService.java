package com.example.springboot1er.service;

import com.example.springboot1er.model.GameCreationParams;
import com.example.springboot1er.model.MoveParams;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Collection;
import java.util.UUID;

public interface GameService {

    Game createGame(GameCreationParams params);

    Game getGame(UUID gameId);

    Collection<CellPosition> getAvailableMoves(UUID gameId, UUID playerId);

    Game playMove(UUID gameId, MoveParams params);

}
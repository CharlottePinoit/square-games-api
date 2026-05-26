package com.example.springboot1er.service;

import com.example.springboot1er.model.GameCreationParams;
import fr.le_campus_numerique.square_games.engine.Game;

import java.util.UUID;

public interface GameService {

    Game createGame(GameCreationParams params);

    Game getGame(UUID gameId);

}
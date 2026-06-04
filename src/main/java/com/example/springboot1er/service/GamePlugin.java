package com.example.springboot1er.service;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;
import java.util.UUID;

public interface GamePlugin {

    String getGameId();

    String getName(Locale locale);

    Game createGame(int boardSize, java.util.Set<UUID> playerIds);

}
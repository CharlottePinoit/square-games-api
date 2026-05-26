package com.example.springboot1er.model;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import java.util.UUID;

public class MoveParams {
    private UUID playerId;
    private CellPosition position;

    public MoveParams(UUID playerId, CellPosition position) {
        this.playerId = playerId;
        this.position = position;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public CellPosition getPosition() {
        return position;
    }
}
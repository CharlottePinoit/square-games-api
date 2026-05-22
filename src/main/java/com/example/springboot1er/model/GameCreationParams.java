package com.example.springboot1er.model;

public class GameCreationParams {
    private String gameType;
    private int playerCount;
    private int boardSize;

    public GameCreationParams(String gameType, int playerCount, int boardSize) {
        this.gameType = gameType;
        this.playerCount = playerCount;
        this.boardSize = boardSize;
    }

    public String getGameType() {
        return gameType;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
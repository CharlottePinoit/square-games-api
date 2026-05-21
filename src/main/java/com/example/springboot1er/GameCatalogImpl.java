package com.example.springboot1er;

import fr.le_campus_numerique.square_games.engine.GameFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final List<GameFactory> gameFactories;

    public GameCatalogImpl(List<GameFactory> gameFactories) {
        this.gameFactories = gameFactories;
    }

    @Override
    public Collection<String> getGameIds() {
        List<String> resultat = new ArrayList<>();
        for (GameFactory factory : gameFactories) {
            resultat.add(factory.getGameFactoryId());
        }
        return resultat;
    }

}

/*package com.example.springboot1er;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final TicTacToeGameFactory factory;

    public GameCatalogImpl(TicTacToeGameFactory factory) {
        this.factory = factory;
    }

    @Override
    public Collection<String> getGameIds() {
        return List.of(factory.getGameFactoryId());
    }

}*/
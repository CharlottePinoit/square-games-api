package com.example.springboot1er;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {

    @Bean
    public TicTacToeGameFactory ticTacToeGameFactory() {
        return new TicTacToeGameFactory();
    }

    @Bean
    public TaquinGameFactory taquinGameFactory() {
        return new TaquinGameFactory();
    }

    @Bean
    public ConnectFourGameFactory connectFourGameFactory() {
        return new ConnectFourGameFactory();
    }

}
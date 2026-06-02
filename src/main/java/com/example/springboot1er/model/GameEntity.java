package com.example.springboot1er.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "game_entity")
public class GameEntity {

    @Id
    public String id;

    public String factoryId;

    public int boardSize;

    public String playerIds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<GameTokenEntity> tokens;

}
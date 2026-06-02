package com.example.springboot1er.model;

import jakarta.persistence.*;

@Entity
@Table(name = "game_token_entity")
public class GameTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incrémentation de postgreSQL
    public Long id;

    public String ownerId;

    public String name;

    public boolean removed;

    public Integer x;

    public Integer y;

}
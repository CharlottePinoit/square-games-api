package com.example.springboot1er.service;

import com.example.springboot1er.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity, String> {

}
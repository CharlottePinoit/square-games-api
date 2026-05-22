package com.example.springboot1er.controller;

import com.example.springboot1er.service.GameCatalog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GameCatalogController {

    private final GameCatalog gameCatalog;

    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    @GetMapping("/catalog")
    public Collection<String> getCatalog() {
        return gameCatalog.getGameIds();
    }

}
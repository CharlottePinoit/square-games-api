package com.example.springboot1er.controller;


import com.example.springboot1er.service.GamePlugin;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class GameCatalogController {

    private final List<GamePlugin> gamePlugins;

    public GameCatalogController(List<GamePlugin> gamePlugins) {
        this.gamePlugins = gamePlugins;
    }

    @GetMapping
    public Collection<String> getGameNames() {
        Locale locale = LocaleContextHolder.getLocale(); // Récupère la langue de la requête
        return gamePlugins.stream()
                .map(plugin -> plugin.getName(locale))
                .collect(Collectors.toList());
    }

}
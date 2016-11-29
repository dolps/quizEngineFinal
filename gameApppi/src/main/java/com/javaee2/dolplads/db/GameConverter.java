package com.javaee2.dolplads.db;

import com.javaee2.dolplads.api.GameDto;
import com.javaee2.dolplads.core.Game;
import com.woact.dolplads.quiz.backend.entity.Quiz;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dolplads on 28/11/2016.
 */
public class GameConverter {
    public static GameDto transform(Game game) {
        Objects.requireNonNull(game);
        GameDto dto = new GameDto();

        return dto;
    }

    public static List<GameDto> transform(List<Game> games) {
        Objects.requireNonNull(games);

        return games.stream()
                .map(GameConverter::transform)
                .collect(Collectors.toList());
    }
}

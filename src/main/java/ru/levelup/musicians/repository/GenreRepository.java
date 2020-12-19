package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Genre;

import java.util.List;

public interface GenreRepository {

    Genre createNewGenre(String genreName);

    List<Genre> findAllGenres();
}

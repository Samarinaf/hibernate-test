package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateGenreRepositoryTest {

    private SessionFactory factory;
    private  Session session;
    private  HibernateGenreRepository repository;

    @BeforeEach
    public void openSession () {
        factory = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateGenreRepository(factory);
    }


    @Test
    public void testCreateNewGenre() {
        Genre genre = repository.createNewGenre("genreName");

        Genre result = session.get(Genre.class, genre.getGenreId());

        assertEquals(genre.getGenreId(), result.getGenreId());
        assertEquals(genre.getGenreName(), result.getGenreName());

    }

    @Test
    public void testFindAllGenres() {
        Genre genre = repository.createNewGenre("genreName0");
        Genre genre1 = repository.createNewGenre("genreName1");
        Genre genre2 = repository.createNewGenre("genreName2");

        List<Genre> testGenres = new ArrayList<>();
        testGenres.add(genre);
        testGenres.add(genre1);
        testGenres.add(genre2);

        List<Genre> resultGenres = repository.findAllGenres();

        assertTrue(resultGenres.containsAll(testGenres));
    }
}

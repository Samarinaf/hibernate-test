package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Genre;
import ru.levelup.musicians.model.Musician;
import ru.levelup.musicians.repository.GenreRepository;

import java.util.List;


public class HibernateGenreRepository extends AbstractRepository implements GenreRepository {

    public HibernateGenreRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Genre createNewGenre(String genreName) {
            return runInTransaction(session -> {
            Genre genre = new Genre();
            genre.setGenreName(genreName);

            session.persist(genre);

            return genre;
        });
    }

    @Override
    public List<Genre> findAllGenres() {
        return runWithoutTransaction(session -> {
            List<Genre> genres = session
                    .createQuery("FROM Genre", Genre.class)
                    .getResultList();

            for (Genre genre: genres) {
                List<Band> bands = genre.getBands();
                bands.forEach(band -> session.get(Band.class, band.getBandId()));
                List<Musician> musicians = genre.getMusicians();
                musicians.forEach(musician -> session.get(Musician.class, musician.getId()));
            }

            return genres;
        });
    }
}

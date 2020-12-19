package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.*;
import ru.levelup.musicians.repository.MusicianRepository;

import java.time.LocalDate;
import java.util.List;

public class HibernateMusicianRepository extends AbstractRepository implements MusicianRepository {

    public HibernateMusicianRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Musician createNewMusician(String firstName, String middleName, String lastName, String sex, LocalDate dateOfBirth, Integer countryId) {
            return runInTransaction(session -> {
            Musician musician = new Musician();
            musician.setFirstName(firstName);
            musician.setMiddleName(middleName);
            musician.setLastName(lastName);
            musician.setSex(sex);
            musician.setDateOfBirth(dateOfBirth);
            musician.setCountry(new Country(countryId));

            session.persist(musician);

            return musician;
        });
    }

    @Override
    public Musician updateMusician(Integer id, String firstName, String middleName, String lastName, String sex, LocalDate dateOfBirth, Integer countryId) {
            return runInTransaction(session -> {
            Musician musician = (Musician) session
                    .createQuery("FROM Musician WHERE id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            musician.setFirstName(firstName);
            musician.setMiddleName(middleName);
            musician.setLastName(lastName);
            musician.setSex(sex);
            musician.setDateOfBirth(dateOfBirth);
            musician.setCountry(new Country(countryId));

            session.update(musician);
            return musician;
        });
    }

    @Override
    public void deleteMusician(Integer id) {
        runInTransaction(session -> {
            Musician musician = (Musician) session
                    .createQuery("FROM Musician WHERE id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            session.delete(musician);
        });
    }

    @Override
    public List<Musician> findByName(String firstName) {
        return runWithoutTransaction(session -> session
                .createQuery("FROM Musician WHERE firstName LIKE :firstName", Musician.class)
                .setParameter("firstName", firstName)
                .getResultList());
    }

    @Override
    public Musician findById(Integer id) {
        return runWithoutTransaction(session -> session
                .createQuery("from Musician where id = :id", Musician.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public List<Musician> findByDateOfBirth(LocalDate dateOfBirth) {
        return runWithoutTransaction(session -> session
                .createQuery("FROM Musician WHERE dateOfBirth = :dateOfBirth", Musician.class)
                .setParameter("dateOfBirth", dateOfBirth)
                .getResultList());
    }

    @Override
    public List<Musician> findAllMusicians() {
        return runWithoutTransaction(session -> {
            List<Musician> musicians = session
                    .createQuery("FROM Musician", Musician.class)
                    .getResultList();

            for (Musician musician: musicians) {
                session.get(Country.class, musician.getCountry().getCountryId());
                List<Album> albums = musician.getAlbums();
                albums.forEach(album -> session.get(Album.class, album.getAlbumId()));
                List<Genre> genres = musician.getGenres();
                genres.forEach(genre -> session.get(Genre.class, genre.getGenreId()));
                List<MusiciansBands> bands = musician.getMusiciansBands();
                bands.forEach(band -> session.get(MusiciansBands.class, band.getId()));
            }

            return musicians;
        });
    }



}

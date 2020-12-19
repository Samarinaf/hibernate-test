package ru.levelup.musicians;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Album;
import ru.levelup.musicians.model.Country;
import ru.levelup.musicians.model.Musician;
import ru.levelup.musicians.model.Song;
import ru.levelup.musicians.repository.*;
import ru.levelup.musicians.repository.impl.*;

import java.time.LocalDate;
import java.util.List;

public class TestApp {
    public static void main(String[] args) {

        SessionFactory factory = HibernateUtils.getSessionFactory();

        CountryRepository countryRepository = new HibernateCountryRepository(factory);
        MusicianRepository musicianRepository = new HibernateMusicianRepository(factory);
        SongRepository songRepository = new HibernateSongRepository(factory);
        AlbumRepository albumRepository = new HibernateAlbumRepository(factory);
        BandRepository bandRepository = new HibernateBandRepository(factory);
        GenreRepository genreRepository = new HibernateGenreRepository(factory);

        //albumRepository.createNewAlbum("album", LocalDate.of(2020, 1, 1));

        //bandRepository.addMusician(1);

        //System.out.println(countryRepository.findCountryByName("hhh"));

        Country country = countryRepository.createNewCountry("countryName");
        countryRepository.deleteCountry(country.getCountryId());
        System.out.println(countryRepository.findCountryByName(country.getCountryName()));

        factory.close();
    }
}

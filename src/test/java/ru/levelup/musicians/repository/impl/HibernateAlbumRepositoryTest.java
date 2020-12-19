package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateAlbumRepositoryTest {

    private SessionFactory factory;
    private Session session;
    private HibernateAlbumRepository repository;

    @BeforeEach
    public void openSession () {
        factory = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateAlbumRepository(factory);
    }

    @Test
    public void testCreateNewAlbum() {

        Album album = repository.createNewAlbum("Moral Panic", LocalDate.of(2020, 10, 23));

        Album result = repository.findById(album.getAlbumId());

        assertEquals(album.getAlbumName(), result.getAlbumName());
        assertEquals(album.getAlbumYear(), result.getAlbumYear());
        assertEquals(album.getAlbumId(), result.getAlbumId());

    }

    @Test
    public void testFindAllAlbums() {
        Album album1 = repository.createNewAlbum("first", LocalDate.of(2020, 10, 23));
        Album album2 = repository.createNewAlbum("second", LocalDate.of(2019, 11, 2));
        Album album3 = repository.createNewAlbum("third", LocalDate.of(2018, 12, 3));

        List<Album> testAlbums = new ArrayList<>();
        testAlbums.add(album1);
        testAlbums.add(album2);
        testAlbums.add(album3);

        List<Album> resultAlbums = repository.findAllAlbums();
        assertTrue(resultAlbums.containsAll(testAlbums));

    }

    @Test
    public void testFindById() {
        Album album = repository.createNewAlbum("Moral Panic", LocalDate.of(2020, 10, 23));

        Album result = repository.findById(album.getAlbumId());

        assertEquals(album.getAlbumId(), result.getAlbumId());
    }

    @Test
    public void testAddMusician() {
        Album testAlbum = repository.createNewAlbum("Moral Panic", LocalDate.of(2020, 10, 23));

        HibernateCountryRepository countryRepository = new HibernateCountryRepository(factory);
        Country country = countryRepository.createNewCountry("country");

        HibernateMusicianRepository musicianRepository = new HibernateMusicianRepository(factory);
        Musician musician = musicianRepository.createNewMusician("name", "middleName", "lastName", "sex", LocalDate.of(2020, 1, 1), country.getCountryId());
        Musician musician1 = musicianRepository.createNewMusician("name1", "middleName1", "lastName1", "sex1", LocalDate.of(2020, 2, 1), country.getCountryId());
        Musician musician2 = musicianRepository.createNewMusician("name2", "middleName2", "lastName2", "sex2", LocalDate.of(2020, 3, 1), country.getCountryId());

        List<Musician> testMusicians = new ArrayList<>();
        testMusicians.add(musician);
        testMusicians.add(musician1);
        testMusicians.add(musician2);

        repository.addMusician(testAlbum.getAlbumId(), musician);
        repository.addMusician(testAlbum.getAlbumId(), musician1);
        repository.addMusician(testAlbum.getAlbumId(), musician2);

        Album resultAlbum = session.get(Album.class, testAlbum.getAlbumId());
        List<Musician> resultMusicians = resultAlbum.getMusicians();

        assertEquals(testMusicians.size(), resultMusicians.size());

        for (int i = 0; i < testMusicians.size(); i++) {
            assertEquals(testMusicians.get(i).getId(), resultMusicians.get(i).getId());
            assertEquals(testMusicians.get(i).getFirstName(), resultMusicians.get(i).getFirstName());
            assertEquals(testMusicians.get(i).getMiddleName(), resultMusicians.get(i).getMiddleName());
            assertEquals(testMusicians.get(i).getLastName(), resultMusicians.get(i).getLastName());
            assertEquals(testMusicians.get(i).getSex(), resultMusicians.get(i).getSex());
            assertEquals(testMusicians.get(i).getDateOfBirth(), resultMusicians.get(i).getDateOfBirth());
            assertEquals(testMusicians.get(i).getCountry().getCountryId(), resultMusicians.get(i).getCountry().getCountryId());
        }


    }

    @Test
    public void testAddBand() {
        Album testAlbum = repository.createNewAlbum("albumName", LocalDate.of(2020, 10, 23));

        HibernateCountryRepository countryRepository = new HibernateCountryRepository(factory);
        Country country = countryRepository.createNewCountry("country");

        HibernateBandRepository bandRepository = new HibernateBandRepository(factory);
        Band band = bandRepository.createNewBand("bandName", "2020-2020", country.getCountryId());
        Band band1 = bandRepository.createNewBand("bandName1", "2020-2021", country.getCountryId());
        Band band2 = bandRepository.createNewBand("bandName2", "2020-2022", country.getCountryId());

        List<Band> testBands = new ArrayList<>();
        testBands.add(band);
        testBands.add(band1);
        testBands.add(band2);

        repository.addBand(testAlbum.getAlbumId(), band);
        repository.addBand(testAlbum.getAlbumId(), band1);
        repository.addBand(testAlbum.getAlbumId(), band2);

        Album resultAlbum = session.get(Album.class, testAlbum.getAlbumId());
        List<Band> resultBands = resultAlbum.getBands();

        assertEquals(testBands.size(), resultBands.size());

        for (int i = 0; i < testBands.size(); i++) {
            assertEquals(testBands.get(i).getBandId(), resultBands.get(i).getBandId());
            assertEquals(testBands.get(i).getBandName(), resultBands.get(i).getBandName());
            assertEquals(testBands.get(i).getYearsActive(), resultBands.get(i).getYearsActive());
            assertEquals(testBands.get(i).getCountry().getCountryId(), resultBands.get(i).getCountry().getCountryId());
        }

    }

    @Test
    public void testAddSong() {
        Album testAlbum = repository.createNewAlbum("albumName", LocalDate.of(2020, 10, 23));

        HibernateSongRepository songRepository = new HibernateSongRepository(factory);
        Song song = songRepository.createNewSong("songName", testAlbum.getAlbumId());
        Song song1 = songRepository.createNewSong("songName1", testAlbum.getAlbumId());
        Song song2 = songRepository.createNewSong("songName2", testAlbum.getAlbumId());

        List<Song> testSongs = new ArrayList<>();
        testSongs.add(song);
        testSongs.add(song1);
        testSongs.add(song2);

        repository.addSong(testAlbum.getAlbumId(), song);
        repository.addSong(testAlbum.getAlbumId(), song1);
        repository.addSong(testAlbum.getAlbumId(), song2);

        Album resultAlbum = session.get(Album.class, testAlbum.getAlbumId());
        List<Song> resultSongs = resultAlbum.getSongs();

        assertEquals(testSongs.size(), resultSongs.size());

        for (int i = 0; i < testSongs.size(); i++) {
            assertEquals(testSongs.get(i).getSongId(), resultSongs.get(i).getSongId());
            assertEquals(testSongs.get(i).getSongName(), resultSongs.get(i).getSongName());
        }
    }

}

package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Song;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateSongRepositoryTest {
    private SessionFactory factory;
    private Session session;
    private HibernateSongRepository repository;

    @BeforeEach
    public void openSession () {
        factory  = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateSongRepository(factory);
    }

    @Test
    public void testCreateNewSong() {
        Song testSong = repository.createNewSong(
                "songName",
                new HibernateAlbumRepository(factory)
                        .createNewAlbum("album", LocalDate.of(1999,1,2))
                        .getAlbumId());

        //Song result = repository.findById(testSong.getSongId());
        Song result = session.get(Song.class, testSong.getSongId());

        assertEquals(testSong.getSongId(), result.getSongId());
        assertEquals(testSong.getSongName(), result.getSongName());
        assertEquals(testSong.getAlbum().getAlbumId(), result.getAlbum().getAlbumId());
    }

    @Test
    public void testFindAllSongs() {
        Song testSong = repository.createNewSong(
                "songName1",
                new HibernateAlbumRepository(factory)
                        .createNewAlbum("album1", LocalDate.of(1999,1,2))
                        .getAlbumId());
        List<Song> testList = new ArrayList<>();
        testList.add(testSong);

        List<Song> resultList = repository.findAllSongs();

        assertTrue(resultList.containsAll(testList));
    }

    @Test
    public void testFindById() {
        Song testSong = repository.createNewSong(
                "songName2",
                new HibernateAlbumRepository(factory)
                        .createNewAlbum("album2", LocalDate.of(1999,1,2))
                        .getAlbumId());

        //Song result = repository.findById(testSong.getSongId());
        Song result = session.get(Song.class, testSong.getSongId());

        assertEquals(testSong.getSongId(), result.getSongId());
        assertEquals(testSong.getSongName(), result.getSongName());
    }
}

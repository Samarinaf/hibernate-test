package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Album;
import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Musician;
import ru.levelup.musicians.model.Song;

import java.time.LocalDate;
import java.util.List;


public interface AlbumRepository {

    Album createNewAlbum(String albumName, LocalDate albumYear);

    List<Album> findAllAlbums();

    Album findById(Integer album_id);

    void addMusician(Integer albumId, Musician musician);

    void addBand(Integer albumId, Band band);

    void addSong(Integer albumId, Song song);

}

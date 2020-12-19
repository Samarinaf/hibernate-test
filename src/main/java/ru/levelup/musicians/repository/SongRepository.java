package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Song;

import java.util.List;

public interface SongRepository {

    Song createNewSong(String songName, Integer albumId);

    List<Song> findAllSongs();

    Song findById(Integer songId);
}

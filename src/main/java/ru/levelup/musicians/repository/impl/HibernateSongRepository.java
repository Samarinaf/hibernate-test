package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.Album;
import ru.levelup.musicians.model.Song;
import ru.levelup.musicians.repository.SongRepository;

import java.util.List;


public class HibernateSongRepository extends AbstractRepository implements SongRepository {

    public HibernateSongRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Song createNewSong(String songName, Integer albumId) {
        return runInTransaction(session -> {
            Song song = new Song();
            song.setSongName(songName);
            song.setAlbum(new Album(albumId));

            session.persist(song);

            return song;
        });
    }

    @Override
    public List<Song> findAllSongs() {
        return runWithoutTransaction(session -> {
            List<Song> songs = session
                    .createQuery("FROM Song", Song.class)
                    .getResultList();

            for (Song song : songs) {
                session.get(Album.class, song.getAlbum().getAlbumId());
            }

            return songs;
        });
    }

    @Override
    public Song findById(Integer songId) {
           return runWithoutTransaction(session -> session
                .createQuery("FROM Song", Song.class)
                .getSingleResult());
    }
}

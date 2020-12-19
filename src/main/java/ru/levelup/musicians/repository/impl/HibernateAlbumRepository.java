package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.Album;
import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Musician;
import ru.levelup.musicians.model.Song;
import ru.levelup.musicians.repository.AlbumRepository;

import java.time.LocalDate;
import java.util.List;

public class HibernateAlbumRepository extends AbstractRepository implements AlbumRepository {

    public HibernateAlbumRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Album createNewAlbum(String albumName, LocalDate albumYear) {
        return runInTransaction(session -> {
            Album album = new Album();
            album.setAlbumName(albumName);
            album.setAlbumYear(albumYear);

            session.persist(album);

            return album;
        });
    }

    @Override
    public List<Album> findAllAlbums() {
        return runWithoutTransaction(session -> {
            List<Album> albums = session
                    .createQuery("FROM Album", Album.class)
                    .getResultList();

            for (Album album: albums) {
                List<Musician> musicians = album.getMusicians();
                musicians.forEach(musician -> session.get(Musician.class, musician.getId()));
                List<Band> bands = album.getBands();
                bands.forEach(band -> session.get(Band.class, band.getBandId()));
                List<Song> songs = album.getSongs();
                songs.forEach(song -> session.get(Song.class, song.getSongId()));
            }

            return albums;
        });
    }

    @Override
    public Album findById(Integer album_id) {
        return runWithoutTransaction(session -> session
                .createQuery("from Album where album_id = :album_id", Album.class)
                .setParameter("album_id", album_id)
                .getSingleResult());
    }

    @Override
    public void addMusician(Integer albumId, Musician musician) {
        runInTransaction(session -> {
            Album album = session.get(Album.class, albumId);
            album.getMusicians().add(musician);
        });
    }

    @Override
    public void addBand(Integer albumId, Band band) {

        runInTransaction(session -> {
            Album album = session.get(Album.class, albumId);
            album.getBands().add(band);
        });
    }

    @Override
    public void addSong(Integer albumId, Song song) {
        runInTransaction(session -> {
            Album album = session.get(Album.class, albumId);
            album.getSongs().add(song);
        });
    }
}

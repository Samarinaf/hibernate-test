package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.*;
import ru.levelup.musicians.repository.BandRepository;

import java.util.List;

public class HibernateBandRepository extends AbstractRepository implements BandRepository {

    public HibernateBandRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Band createNewBand(String bandName, String yearsActive, Integer countryId) {
            return runInTransaction(session -> {
            Band band = new Band();
            band.setBandName(bandName);
            band.setYearsActive(yearsActive);
            band.setCountry(new Country(countryId));

            session.persist(band);

            return band;
        });
    }

    @Override
    public List<Band> findAllBands() {
        return runWithoutTransaction(session -> {
            List<Band> bands = session
                    .createQuery("FROM Band", Band.class)
                    .getResultList();

            for (Band band: bands) {
                session.get(Country.class, band.getCountry().getCountryId());
                List<MusiciansBands> musiciansBands = band.getMusiciansBands();
                musiciansBands.forEach(mb -> session.get(MusiciansBands.class, mb.getId()));
                List<Album> albums = band.getAlbums();
                albums.forEach(album -> session.get(Album.class, album.getAlbumId()));
                List<Genre> genres = band.getGenres();
                genres.forEach(genre -> session.get(Genre.class, genre.getGenreId()));
            }

            return bands;
        });
    }

    @Override
    public void addMusician(Integer bandId, Musician musician) {
       runInTransaction(session -> {
           Band band = session.get(Band.class, bandId);
           MusiciansBands musiciansBands = new MusiciansBands();
           musiciansBands.setMusician(musician);
           band.getMusiciansBands().add(musiciansBands);
       });
    }
}

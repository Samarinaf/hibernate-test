package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Musician;

import java.util.List;

public interface BandRepository {

    Band createNewBand(String bandName, String yearsActive, Integer countryId);

    List<Band> findAllBands();

    void addMusician(Integer bandId, Musician musician);

}

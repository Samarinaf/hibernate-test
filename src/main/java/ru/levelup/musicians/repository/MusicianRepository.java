package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Musician;

import java.time.LocalDate;
import java.util.List;

public interface MusicianRepository {

    Musician createNewMusician(String firstName, String middleName, String lastName, String sex, LocalDate dateOfBirth, Integer countryId);

    Musician updateMusician(Integer id, String firstName, String middleName, String lastName, String sex, LocalDate dateOfBirth, Integer countryId);

    void deleteMusician(Integer id);

    List<Musician> findByName(String firstName);

    Musician findById(Integer id);

    List<Musician> findByDateOfBirth(LocalDate dateOfBirth);

    List<Musician> findAllMusicians();
}

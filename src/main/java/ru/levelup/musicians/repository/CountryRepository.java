package ru.levelup.musicians.repository;

import ru.levelup.musicians.model.Country;

import java.util.Collection;
import java.util.List;

public interface CountryRepository {

    Country createNewCountry(String countryName);

    List<Country> findAllCountries();

    Country findCountryByName(String countryName);

    void deleteCountry(Integer countryId);
}

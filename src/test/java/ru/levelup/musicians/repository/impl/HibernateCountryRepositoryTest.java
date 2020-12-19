package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Country;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateCountryRepositoryTest {

    private SessionFactory factory;
    private Session session;
    private HibernateCountryRepository repository;

    @BeforeEach
    public void openSession() {
        factory = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateCountryRepository(factory);
    }

    @Test
    public void testCreateNewCountry() {
        Country country = repository.createNewCountry("country");

        //Country result = repository.findCountryByName(country.getCountryName());
        Country result = session.get(Country.class, country.getCountryId());

        assertEquals(country.getCountryId(), result.getCountryId());
        assertEquals(country.getCountryName(), result.getCountryName());
    }

    @Test
    public void testFindAllCountries() {
        Country country = repository.createNewCountry("countryName");
        Country country1 = repository.createNewCountry("countryName1");
        Country country2 = repository.createNewCountry("countryName2");

        List<Country> testCountries = new ArrayList<>();
        testCountries.add(country);
        testCountries.add(country1);
        testCountries.add(country2);

        List<Country> resultCountries = repository.findAllCountries();

        assertTrue(resultCountries.containsAll(testCountries));
    }

    @Test
    public void testFindCountryByName() {
        Country country = repository.createNewCountry("countryName3");

        Country result = repository.findCountryByName(country.getCountryName());

        assertEquals(country.getCountryId(), result.getCountryId());
        assertEquals(country.getCountryName(), result.getCountryName());
    }

    @Test
    public void testDeleteCountry() {
        Country country = repository.createNewCountry("countryName4");

        repository.deleteCountry(country.getCountryId());

        assertThrows(NoResultException.class, () -> repository.findCountryByName(country.getCountryName()));
        //assertThrows(NoResultException.class, () -> session.get(Country.class, country.getCountryId()));
    }
}

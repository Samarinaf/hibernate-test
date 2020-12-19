package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Country;
import ru.levelup.musicians.model.Musician;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateMusicianRepositoryTest {
    private SessionFactory factory;
    private Session session;
    private HibernateMusicianRepository repository;

    @BeforeEach
    public void openSession () {
        factory = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateMusicianRepository(factory);
    }

    @Test
    public void testCreateNewMusician() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "sex",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());

        Musician result = repository.findById(musician.getId());

        assertEquals(musician.getFirstName(), result.getFirstName());
        assertEquals(musician.getMiddleName(), result.getMiddleName());
        assertEquals(musician.getLastName(), result.getLastName());
        assertEquals(musician.getSex(), result.getSex());
        assertEquals(musician.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(musician.getCountry().getCountryId(), result.getCountry().getCountryId());
    }

    @Test
    public void testUpdateMusician() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "male",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());
        Musician updatedMusician = repository.updateMusician(
                musician.getId(),
                "newName",
                "newMiddleName",
                "newLastName",
                "female",
                LocalDate.of(20202,2,2),
                new HibernateCountryRepository(factory).createNewCountry("newCountry").getCountryId());

        Musician result = repository.findById(musician.getId());
        assertEquals(updatedMusician.getId(), result.getId());
        assertEquals(updatedMusician.getFirstName(), result.getFirstName());
        assertEquals(updatedMusician.getMiddleName(), result.getMiddleName());
        assertEquals(updatedMusician.getLastName(), result.getLastName());
        assertEquals(updatedMusician.getSex(), result.getSex());
        assertEquals(updatedMusician.getDateOfBirth(), result.getDateOfBirth());
        assertEquals(updatedMusician.getCountry().getCountryId(), result.getCountry().getCountryId());
    }

    @Test
    public void testDeleteMusician() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "sex",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());
        repository.deleteMusician(musician.getId());

        assertThrows(NoResultException.class, () -> repository.findById(musician.getId()));
    }

    @Test
    public void testFindByName() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "sex",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());

        List<Musician> result = repository.findByName(musician.getFirstName());
        result.forEach(m -> assertEquals(musician.getFirstName(), m.getFirstName()));

    }

    @Test
    public void testFindById() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "sex",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());

        Musician result = repository.findById(musician.getId());
        assertEquals(musician.getId(), result.getId());
    }

    @Test
    public void testFindByDateOfBirth() {
        Musician musician = repository.createNewMusician(
                "name",
                "middleName",
                "lastName",
                "sex",
                LocalDate.of(2020, 1, 1),
                new HibernateCountryRepository(factory).createNewCountry("country").getCountryId());

        List<Musician> result = repository.findByDateOfBirth(musician.getDateOfBirth());
        result.forEach(m -> assertEquals(musician.getDateOfBirth(), m.getDateOfBirth()));
    }

    @Test
    public void testFindAllMusicians() {
        Country country = new HibernateCountryRepository(factory).createNewCountry("country");

        Musician musician = repository.createNewMusician("name", "middleName", "lastName", "sex", LocalDate.of(2020, 1, 1), country.getCountryId());
        Musician musician1 = repository.createNewMusician("name1", "middleName1", "lastName1", "sex1", LocalDate.of(2020, 2, 1), country.getCountryId());
        Musician musician2 = repository.createNewMusician("name2", "middleName2", "lastName2", "sex2", LocalDate.of(2020, 3, 1), country.getCountryId());

        List<Musician> testMusicians = new ArrayList<>();
        testMusicians.add(musician);
        testMusicians.add(musician1);
        testMusicians.add(musician2);

        List<Musician> resultMusicians = repository.findAllMusicians();

        assertTrue(resultMusicians.containsAll(testMusicians));

    }
}

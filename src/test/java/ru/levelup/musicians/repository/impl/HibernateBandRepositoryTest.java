package ru.levelup.musicians.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelup.musicians.hibernate.HibernateUtils;
import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Country;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HibernateBandRepositoryTest {

    private SessionFactory factory;
    private Session session;
    private HibernateBandRepository repository;

    @BeforeEach
    public void openSession () {
        factory = HibernateUtils.getSessionFactory();
        session = factory.openSession();
        repository = new HibernateBandRepository(factory);
    }

    @Test
    public void testCreateNewBand() {
        Band band = repository.createNewBand(
                "bandName",
                "2020-present",
                new HibernateCountryRepository(factory).createNewCountry("countryName").getCountryId());

        Band result = session.get(Band.class, band.getBandId());

        assertEquals(band.getBandName(), result.getBandName());
        assertEquals(band.getCountry().getCountryId(), result.getCountry().getCountryId());
    }

    @Test
    public void testFindAllBands() {
        Country country = new HibernateCountryRepository(factory).createNewCountry("countryName");
        Band band = repository.createNewBand("bandName","2020-present", country.getCountryId());
        Band band1 = repository.createNewBand("bandName1","2021-present", country.getCountryId());
        Band band2 = repository.createNewBand("bandName2","2022-present", country.getCountryId());

        List<Band> testBands = new ArrayList<>();
        testBands.add(band);
        testBands.add(band1);
        testBands.add(band2);

        List<Band> resultBands = repository.findAllBands();
        assertTrue(resultBands.containsAll(testBands));

    }
}

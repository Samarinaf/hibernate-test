package ru.levelup.musicians.repository.impl;

import org.hibernate.SessionFactory;
import ru.levelup.musicians.model.Band;
import ru.levelup.musicians.model.Country;
import ru.levelup.musicians.model.Musician;
import ru.levelup.musicians.repository.CountryRepository;

import javax.persistence.NoResultException;
import java.util.List;

public class HibernateCountryRepository extends AbstractRepository implements CountryRepository {

    public HibernateCountryRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Country createNewCountry(String countryName) {
            return runInTransaction(session -> {
            Country country = new Country();
            country.setCountryName(countryName);

            session.persist(country);

            return country;
        });
    }

    @Override
    public List<Country> findAllCountries() {
        return runWithoutTransaction(session -> {
            List<Country> countries = session
                    .createQuery("FROM Country", Country.class) //HQL
                    .getResultList();

            for (Country country: countries) {
                try {
                    List<Musician> musicians = country.getMusicians();
                    musicians.forEach(musician -> session.get(Musician.class, musician.getId()));
                    List<Band> bands = country.getBands();
                    bands.forEach(band -> session.get(Band.class, band.getBandId()));
                } catch (NullPointerException ignored) {}
            }

            return countries;
            });
    }

    @Override
    public Country findCountryByName(String countryName) {
        //try {
            return runWithoutTransaction(session -> session
                    .createQuery("FROM Country WHERE countryName LIKE :countryName", Country.class)
                    .setParameter("countryName", countryName)
                    .getSingleResult());
//        } catch (NoResultException e) {
//            return null;
//        }
    }

    @Override
    public void deleteCountry(Integer countryId) {
        runInTransaction(session -> {
            Country country = (Country) session
                    .createQuery("FROM Country WHERE countryId = :countryId")
                    .setParameter("countryId", countryId)
                    .getSingleResult();
            session.delete(country);
        });
    }
}

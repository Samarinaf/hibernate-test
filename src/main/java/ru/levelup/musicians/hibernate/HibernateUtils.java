package ru.levelup.musicians.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private HibernateUtils() {}

    private static SessionFactory sessionFactory;

    static {

        Configuration configuration = new Configuration()
                .configure();
        sessionFactory = configuration.buildSessionFactory();

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

package ru.levelup.musicians.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractRepository {

    protected final SessionFactory factory;

    protected void runInTransaction(Consumer<Session> consumer) { //для void запросов
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            consumer.accept(session);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                System.out.println("Rollback transaction");
                tx.rollback();
            }
        }
    }

    //interface function
    protected <T> T runInTransaction(Function<Session, T> function) { //для return запросов
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            T result = function.apply(session);

            tx.commit();

            return result;
        } catch (Exception e) {
            if (tx != null) {
                System.out.println("Rollback transaction");
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    protected <T> T runWithoutTransaction(Function<Session, T> function) { //для select запросов
        try (Session session = factory.openSession()) {
            return function.apply(session); //вернет Т
        }
    }
}

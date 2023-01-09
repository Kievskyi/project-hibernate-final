package org.denysdudnik.service;

import org.denysdudnik.dao.CityDao;
import org.denysdudnik.domain.City;
import org.denysdudnik.domain.CountryLanguage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

public class MySQLService {
    private final SessionFactory sessionFactory;
    private final CityDao cityDao;

    public MySQLService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        cityDao = new CityDao(sessionFactory);
    }

    public void testMysqlData(List<Integer> ids) {
        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            for (Integer id : ids) {
                City city = cityDao.getById(id, session);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }

            session.getTransaction().commit();
        }
    }
}

package org.denysdudnik.dao;

import org.denysdudnik.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDao extends Dao<City> {

    public CityDao(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    @Override
    public City getById(Integer id, Session session) {
        Query<City> query = session.createQuery("select c from City c join fetch c.country where c.id = :ID", City.class);
        query.setParameter("ID", id);

        return query.getSingleResult();
    }
}

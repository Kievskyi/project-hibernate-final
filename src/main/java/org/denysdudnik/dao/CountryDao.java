package org.denysdudnik.dao;

import org.denysdudnik.domain.Country;
import org.hibernate.SessionFactory;

public class CountryDao extends Dao<Country> {

    public CountryDao(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}

package org.denysdudnik.dao;

import org.denysdudnik.domain.CountryLanguage;
import org.hibernate.SessionFactory;

public class CountryLanguageDao extends Dao<CountryLanguage> {

    public CountryLanguageDao(SessionFactory sessionFactory) {
        super(CountryLanguage.class, sessionFactory);
    }
}

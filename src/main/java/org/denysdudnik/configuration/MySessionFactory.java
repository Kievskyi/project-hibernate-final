package org.denysdudnik.configuration;

import org.hibernate.SessionFactory;

public interface MySessionFactory {

    SessionFactory getSessionFactory();
}

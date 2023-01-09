package org.denysdudnik.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class Dao<T> {
    private final Class<T> clazz;
    private final SessionFactory sessionFactory;
    private final String alias;


    public Dao(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.alias = changeFirstLetterToLowerCase(clazz);
        this.sessionFactory = sessionFactory;
    }

    public T getById(Integer id, Session session) {
        return session.get(clazz, id);
    }

    public List<T> getAll() {
        String hql = buildHqlWithFetchJoin(clazz);

        Query<T> query = sessionFactory.getCurrentSession()
                .createQuery(hql, clazz);

        return query.list();
    }

    public List<T> getItems(int offset, int limit) {
        Query<T> query = sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName(), clazz);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.list();
    }

    public Long getTotalCount() {
        return sessionFactory.getCurrentSession().createQuery("select count(*) from " + clazz.getSimpleName(), Long.class).uniqueResult();
    }

    private String changeFirstLetterToLowerCase(Class<T> clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase();
    }

    private String buildHqlWithFetchJoin(Class<T> clazz) {
        return "select " + alias +
                " from " + clazz.getSimpleName() + " " + alias +
                " join fetch " + alias + ".languages";
    }
}

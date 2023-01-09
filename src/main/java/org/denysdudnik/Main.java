package org.denysdudnik;


import lombok.Getter;
import org.denysdudnik.configuration.MySQLSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Getter
public class Main {
    private final SessionFactory sessionFactory;

    public Main() {
        sessionFactory = new MySQLSessionFactory().getSessionFactory();
    }

    public static void main(String[] args) {
        Main main = new Main();

        try (Session session = main.getSessionFactory().openSession()) {
            Dispatcher dispatcher = new Dispatcher(main.getSessionFactory());
            dispatcher.start();
        }
    }
}

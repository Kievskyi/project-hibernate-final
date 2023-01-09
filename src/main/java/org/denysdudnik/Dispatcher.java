package org.denysdudnik;

import org.denysdudnik.handlers.Handler;
import org.denysdudnik.handlers.impl.DataHandler;
import org.hibernate.SessionFactory;

public class Dispatcher {
    private final Handler handler;

    /**
     * The variable "handler" must be initialized by any existing handler from handlers package.
     * Initialization of this variable represents users request.
     */
    public Dispatcher(SessionFactory sessionFactory) {
        handler = new DataHandler(sessionFactory);
    }

    public void start() {
        handler.handle();
    }
}

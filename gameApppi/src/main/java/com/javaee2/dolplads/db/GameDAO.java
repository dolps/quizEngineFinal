package com.javaee2.dolplads.db;

import com.javaee2.dolplads.core.Game;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by dolplads on 27/11/2016.
 */
public class GameDAO extends AbstractDAO<Game> {

    public GameDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Game findById(int id) {
        return get(id);
    }

    public Game save(Game game) {
        return super.persist(game);
    }

    public List<Game> findAll() {
        return super.list(criteria());
    }
}
package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDAO implements DAO<Movie> {

    private SessionFactory sessionFactory;

    public MovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public List<Movie> findAll() {

        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Movie", Movie.class ).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Movie>(0);
        }
    }

    @Override
    public Movie findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Movie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Movie Movie) {
        sessionFactory.inTransaction(session -> session.persist(Movie));
    }

    @Override
    public void update(Movie Movie) {
        sessionFactory.inTransaction(
                session -> session.merge(Movie)
                /*  session -> {
                    session.beginTransaction();
                    Movie g = session.get(Movie.class, Movie.getId());
                    g.setTitle(Movie.getTitle());
                    g.setPlatform(Movie.getPlatform());
                    g.setYear(Movie.getYear());
                    g.setDescription(Movie.getDescription());
                    g.setImageUrl(Movie.getImageUrl());
                }*/
        );
    }

    @Override
    public void delete(Movie Movie) {
        sessionFactory.inTransaction(session -> session.remove(Movie));
    }

    public ArrayList<Movie> findByUser(User user) {
        try(Session session = sessionFactory.openSession()){
            return (ArrayList<Movie>) session.createQuery("select movie from Copia WHERE user.id = :userId", Movie.class)
                    .setParameter("userId", user.getId())
                    .list();
        }
    }

    public Set<String> findGenres() {
        try(Session session = sessionFactory.openSession()){
            return new HashSet<>(session.createQuery("select distinct genero from Movie", String.class).getResultList());
        }
    }
}

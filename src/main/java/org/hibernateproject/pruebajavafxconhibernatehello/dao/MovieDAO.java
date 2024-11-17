package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase DAO para gestionar las operaciones CRUD de la entidad Movie.
 */
public class MovieDAO implements DAO<Movie> {

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el DAO con una SessionFactory.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate
     */
    public MovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todas las películas de la base de datos.
     *
     * @return una lista de todas las películas
     */
    @Override
    public List<Movie> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Movie", Movie.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Movie>(0);
        }
    }

    /**
     * Busca una película por su ID.
     *
     * @param id el ID de la película
     * @return la película encontrada o null si no se encuentra
     */
    @Override
    public Movie findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Movie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda una nueva película en la base de datos.
     *
     * @param movie la película a guardar
     */
    @Override
    public void save(Movie movie) {
        sessionFactory.inTransaction(session -> session.persist(movie));
    }

    /**
     * Actualiza una película existente en la base de datos.
     *
     * @param movie la película a actualizar
     */
    @Override
    public void update(Movie movie) {
        sessionFactory.inTransaction(session -> session.merge(movie));
    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param movie la película a eliminar
     */
    @Override
    public void delete(Movie movie) {
        sessionFactory.inTransaction(session -> session.remove(movie));
    }

    /**
     * Busca películas por usuario.
     *
     * @param user el usuario
     * @return una lista de películas asociadas al usuario
     */
    public ArrayList<Movie> findByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            return (ArrayList<Movie>) session.createQuery("select movie from Copia WHERE user.id = :userId", Movie.class)
                    .setParameter("userId", user.getId())
                    .list();
        }
    }

    /**
     * Obtiene un conjunto de géneros distintos de las películas.
     *
     * @return un conjunto de géneros distintos
     */
    public Set<String> findGenres() {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(session.createQuery("select distinct genero from Movie", String.class).getResultList());
        }
    }
}
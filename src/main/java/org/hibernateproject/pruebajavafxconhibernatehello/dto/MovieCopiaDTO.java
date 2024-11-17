package org.hibernateproject.pruebajavafxconhibernatehello.dto;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Copia;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Movie;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieCopiaDTO {
    private Movie movie;
    private Copia copia;

    private SessionFactory sessionFactory;

    public MovieCopiaDTO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MovieCopiaDTO(Movie movie, Copia copia) {
        this.movie = movie;
        this.copia = copia;
    }

    public List<MovieCopiaDTO> findAllMovieCopiaDTO(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT m, c " +
                                    "FROM Copia c JOIN c.movie m " +
                                    "WHERE c.user.id = :user", MovieCopiaDTO.class)
                    .setParameter("user", user.getId())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

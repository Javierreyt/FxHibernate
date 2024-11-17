package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.dto.MovieCopiaDTO;
import org.hibernateproject.pruebajavafxconhibernatehello.model.Copia;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CopiaDAO implements DAO<Copia>{

    private SessionFactory sessionFactory;

    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public List<Copia> findAll() {

        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Copia", Copia.class ).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Copia>(0);
        }
    }

    @Override
    public Copia findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Copia.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Copia Copia) {
        sessionFactory.inTransaction(session -> session.persist(Copia));
    }

    @Override
    public void update(Copia Copia) {
        sessionFactory.inTransaction(
                session -> session.merge(Copia)
                /*  session -> {
                    session.beginTransaction();
                    Copia g = session.get(Copia.class, Copia.getId());
                    g.setTitle(Copia.getTitle());
                    g.setPlatform(Copia.getPlatform());
                    g.setYear(Copia.getYear());
                    g.setDescription(Copia.getDescription());
                    g.setImageUrl(Copia.getImageUrl());
                }*/
        );
    }

    @Override
    public void delete(Copia Copia) {
        sessionFactory.inTransaction(session -> session.remove(Copia));
    }


    public Set<String> findSoporte() {
        try(Session session = sessionFactory.openSession()){
            return new HashSet<>(session.createQuery("select distinct soporte from Copia", String.class).getResultList());
        }
    }


    public Set<String> findEstado() {
        try(Session session = sessionFactory.openSession()){
            return new HashSet<>(session.createQuery("select distinct estado from Copia", String.class).getResultList());
        }
    }

}

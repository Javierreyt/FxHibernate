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

/**
 * Clase DAO para gestionar las operaciones CRUD de la entidad Copia.
 */
public class CopiaDAO implements DAO<Copia>{

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el DAO con una SessionFactory.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate
     */
    public CopiaDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todas las copias de la base de datos.
     *
     * @return una lista de todas las copias
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Copia>(0);
        }
    }

    /**
     * Busca una copia por su ID.
     *
     * @param id el ID de la copia
     * @return la copia encontrada o null si no se encuentra
     */
    @Override
    public Copia findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Copia.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param copia la copia a guardar
     */
    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction(session -> session.persist(copia));
    }

    /**
     * Actualiza una copia existente en la base de datos.
     *
     * @param copia la copia a actualizar
     */
    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction(session -> session.merge(copia));
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param copia la copia a eliminar
     */
    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction(session -> session.remove(copia));
    }

    /**
     * Obtiene un conjunto de soportes distintos de las copias.
     *
     * @return un conjunto de soportes distintos
     */
    public Set<String> findSoporte() {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(session.createQuery("select distinct soporte from Copia", String.class).getResultList());
        }
    }

    /**
     * Obtiene un conjunto de estados distintos de las copias.
     *
     * @return un conjunto de estados distintos
     */
    public Set<String> findEstado() {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(session.createQuery("select distinct estado from Copia", String.class).getResultList());
        }
    }
}
package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que implementa el DAO (Data Access Object) para la entidad User.
 */
public class UserDAO implements DAO<User>{

    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el DAO con una SessionFactory.
     *
     * @param sessionFactory La SessionFactory de Hibernate.
     */
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene una lista de todos los usuarios.
     *
     * @return Una lista de usuarios.
     */
    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>(0);
        }
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no se encuentra.
     */
    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param user El usuario a guardar.
     */
    @Override
    public void save(User user) {
        sessionFactory.inTransaction(session -> session.persist(user));
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param user El usuario a actualizar.
     */
    @Override
    public void update(User user) {
        sessionFactory.inTransaction(session -> session.merge(user));
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param user El usuario a eliminar.
     */
    @Override
    public void delete(User user) {
        sessionFactory.inTransaction(session -> session.remove(user));
    }

    /**
     * Valida un usuario por su nombre de usuario y contraseña.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return El usuario validado o null si no se encuentra.
     */
    public User validateUser(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User where nombreUsuario = :nombre_usuario and contraseña = :contraseña", User.class)
                    .setParameter("nombre_usuario", username)
                    .setParameter("contraseña", password)
                    .uniqueResult();
        }
    }

    /**
     * Verifica si un usuario es administrador.
     *
     * @param user El usuario a verificar.
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public Boolean isAdmin(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT isAdmin FROM User WHERE id = :userId", Boolean.class)
                    .setParameter("userId", user.getId())
                    .uniqueResult();
        }
    }

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     *
     * @param username El nombre de usuario a verificar.
     * @return true si el nombre de usuario existe, false en caso contrario.
     */
    public Boolean userExists(String username) {
        Boolean exists = false;
        try (Session session = sessionFactory.openSession()) {
            Long count = (Long) session.createQuery("SELECT count(*) FROM User WHERE nombreUsuario = :nombre_usuario")
                    .setParameter("nombre_usuario", username)
                    .uniqueResult();

            if (count > 0) {
                exists = true;
            } else {
                exists = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}
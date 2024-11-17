package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserDAO implements DAO<User>{

    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public List<User> findAll() {

        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from User", User.class ).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<User>(0);
        }
    }

    @Override
    public User findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(User User) {
        sessionFactory.inTransaction(session -> session.persist(User));
    }

    @Override
    public void update(User User) {
        sessionFactory.inTransaction(
                session -> session.merge(User)
                /*  session -> {
                    session.beginTransaction();
                    User g = session.get(User.class, User.getId());
                    g.setTitle(User.getTitle());
                    g.setPlatform(User.getPlatform());
                    g.setYear(User.getYear());
                    g.setDescription(User.getDescription());
                    g.setImageUrl(User.getImageUrl());
                }*/
        );
    }

    @Override
    public void delete(User User) {
        sessionFactory.inTransaction(session -> session.remove(User));
    }

    public User validateUser(String username, String password) {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from User where nombreUsuario = :nombre_usuario and contraseña = :contraseña", User.class)
                    .setParameter("nombre_usuario", username)
                    .setParameter("contraseña", password)
                    .uniqueResult();
        }
    }

    public Boolean isAdmin(User user) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT isAdmin FROM User WHERE id = :userId", Boolean.class)
                    .setParameter("userId", user.getId())
                    .uniqueResult();
        }
    }

    public Boolean userExists(String username) {
        Boolean exists = false;
        try (Session session = sessionFactory.openSession()) {
            // Utilizamos una consulta HQL (Hibernate Query Language) para contar los usuarios con el nombre de usuario dado.
            Long count = (Long) session.createQuery("SELECT count(*) FROM User WHERE nombreUsuario = :nombre_usuario")
                    .setParameter("nombre_usuario", username)
                    .uniqueResult();

            if (count > 0){
                exists = true;
            }else exists = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}

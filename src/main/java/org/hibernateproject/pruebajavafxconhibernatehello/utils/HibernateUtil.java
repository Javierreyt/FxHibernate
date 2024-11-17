package org.hibernateproject.pruebajavafxconhibernatehello.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilidad para gestionar la configuración de Hibernate y la creación de la SessionFactory.
 */
public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password", System.getenv("MYSQL_ROOT_PASSWORD"))
                .setProperty("hibernate.connection.username", System.getenv("MYSQL_USER"))
                .buildSessionFactory();
    }
}
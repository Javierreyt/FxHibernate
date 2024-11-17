package org.hibernateproject.pruebajavafxconhibernatehello.utils;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteUtil {

    // Ruta de la base de datos SQLite
    private static String DATABASE_URL = "jdbc:sqlite:localDB.db"; // Puedes cambiar "database.db" por la ruta a tu base de datos

    /**
     * Conexión única a la base de datos.
     */
    @Getter
    private static Connection connection;

    /**
     * Bloque estático que se ejecuta al cargar la clase. Establece la conexión con la base de datos
     * utilizando los parámetros de conexión (URL, usuario, y contraseña). La contraseña se obtiene
     * de una variable de entorno "MYSQL_ROOT_PASSWORD".
     *
     * @throws RuntimeException Si ocurre un error al intentar conectarse a la base de datos.
     */
    static {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
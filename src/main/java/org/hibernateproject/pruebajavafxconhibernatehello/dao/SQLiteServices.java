package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import org.hibernateproject.pruebajavafxconhibernatehello.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que proporciona servicios para interactuar con la base de datos SQLite.
 */
public class SQLiteServices {
    private Connection connection;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param connection La conexión a la base de datos.
     */
    public SQLiteServices(Connection connection) {
        this.connection = connection;
    }

    /**
     * Verifica si un usuario existe en la base de datos por su ID.
     *
     * @param usuario El usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean verificarUsuarioPorId(User usuario) {
        String sql = "SELECT COUNT(*) FROM main.User WHERE id_user = ?";
        boolean existe = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, usuario.getId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;  // Si el conteo es mayor que 0, el usuario existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a insertar.
     * @param remindMe Valor booleano para el campo remind_me.
     * @param help Valor booleano para el campo help.
     */
    public void insertarUsuario(User usuario, boolean remindMe, boolean help) {
        String sql = "INSERT INTO main.User (id_user, nombre_usuario, contraseña, is_admin, remind_me, help) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, usuario.getId());
            pstmt.setString(2, usuario.getNombreUsuario());
            pstmt.setString(3, usuario.getContraseña());
            pstmt.setBoolean(4, usuario.getIsAdmin());
            pstmt.setObject(5, remindMe, java.sql.Types.BOOLEAN);
            pstmt.setObject(6, help, java.sql.Types.BOOLEAN);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el valor de remind_me para un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El valor de remind_me.
     */
    public Boolean remindMeByUser(Long id) {
        String sql = "SELECT remind_me FROM main.User WHERE id_user = ?";
        Boolean remindMe = false;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                remindMe = rs.getBoolean("remind_me");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return remindMe;
    }

    /**
     * Actualiza el valor de remind_me para un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @param remindMe El nuevo valor de remind_me.
     */
    public void updateRemindMe(Long id, boolean remindMe) {
        String sql = "UPDATE main.User SET remind_me = ? WHERE id_user = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, remindMe);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el valor de help para un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El valor de help.
     */
    public Boolean helpByUser(Long id) {
        String sql = "SELECT help FROM main.User WHERE id_user = ?";
        Boolean help = false;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                help = rs.getBoolean("help");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return help;
    }

    /**
     * Actualiza el valor de help para un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @param help El nuevo valor de help.
     */
    public void updateHelp(Long id, boolean help) {
        String sql = "UPDATE main.User SET help = ? WHERE id_user = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, help);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
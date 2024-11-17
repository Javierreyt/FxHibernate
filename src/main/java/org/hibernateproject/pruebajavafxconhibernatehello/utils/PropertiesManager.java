package org.hibernateproject.pruebajavafxconhibernatehello.utils;

import java.io.*;
import java.util.Base64;
import java.util.Properties;

/**
 * Clase de utilidad para gestionar las propiedades de la aplicación.
 */
public class PropertiesManager {
    private static final String PROPERTIES_FILE = "src/main/java/org/hibernateproject/pruebajavafxconhibernatehello/utils/session.properties";
    private static Properties properties;

    private PropertiesManager() {
        throw new IllegalStateException("Utility class");
    }

    static {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de propiedades: " + e.getMessage());
        }
    }

    /**
     * Guarda un objeto serializable en las propiedades.
     *
     * @param key    la clave bajo la cual se guarda el objeto
     * @param object el objeto a guardar
     * @param <T>    el tipo del objeto
     */
    public static <T extends Serializable> void saveObject(String key, T object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            String objectBase64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            properties.setProperty(key, objectBase64);
            saveProperties();
        } catch (IOException e) {
            System.out.println("Error al serializar el objeto: " + e.getMessage());
        }
    }

    /**
     * Carga un objeto serializable de las propiedades.
     *
     * @param key   la clave bajo la cual se guarda el objeto
     * @param clazz la clase del objeto
     * @param <T>   el tipo del objeto
     * @return el objeto cargado o null si no se encuentra
     */
    @SuppressWarnings("unchecked")
    public static <T> T loadObject(String key, Class<T> clazz) {
        try {
            String objectBase64 = properties.getProperty(key);
            if (objectBase64 != null) {
                byte[] objectBytes = Base64.getDecoder().decode(objectBase64);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (T) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar el objeto: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene el nombre de usuario de las propiedades.
     *
     * @return el nombre de usuario
     */
    public static String getUserNombreUsuario() {
        return properties.getProperty("user.nombre_usuario");
    }

    /**
     * Obtiene la contraseña de usuario de las propiedades.
     *
     * @return la contraseña de usuario
     */
    public static String getUserContraseña() {
        return properties.getProperty("user.contrasena");
    }

    /**
     * Verifica si se debe recordar al usuario.
     *
     * @return true si se debe recordar al usuario, false en caso contrario
     */
    public static boolean isRecordarUsuario() {
        return Boolean.parseBoolean(properties.getProperty("recordarUsuario", "false"));
    }

    /**
     * Verifica si se debe mostrar información adicional.
     *
     * @return true si se debe mostrar información adicional, false en caso contrario
     */
    public static boolean isInfo() {
        return Boolean.parseBoolean(properties.getProperty("info", "false"));
    }

    /**
     * Establece el nombre de usuario en las propiedades.
     *
     * @param nombreUsuario el nombre de usuario a establecer
     */
    public static void setUserNombreUsuario(String nombreUsuario) {
        properties.setProperty("user.nombre_usuario", nombreUsuario);
        saveProperties();
    }

    /**
     * Establece la contraseña de usuario en las propiedades.
     *
     * @param contraseña la contraseña de usuario a establecer
     */
    public static void setUserContraseña(String contraseña) {
        properties.setProperty("user.contrasena", contraseña);
        saveProperties();
    }

    /**
     * Establece si se debe recordar al usuario.
     *
     * @param recordarUsuario true si se debe recordar al usuario, false en caso contrario
     */
    public static void setRecordarUsuario(boolean recordarUsuario) {
        properties.setProperty("recordarUsuario", Boolean.toString(recordarUsuario));
        saveProperties();
    }

    /**
     * Establece si se debe mostrar información adicional.
     *
     * @param info true si se debe mostrar información adicional, false en caso contrario
     */
    public static void setInfo(boolean info) {
        properties.setProperty("info", Boolean.toString(info));
        saveProperties();
    }

    /**
     * Guarda los cambios en el archivo de propiedades.
     */
    private static void saveProperties() {
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, "Propiedades de la aplicación");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de propiedades: " + e.getMessage());
        }
    }

    /**
     * Establece si se debe guardar una copia.
     *
     * @param saveCopy true si se debe guardar una copia, false en caso contrario
     */
    public static void setSaveCopy(boolean saveCopy) {
        properties.setProperty("savecopy", Boolean.toString(saveCopy));
        saveProperties();
    }

    /**
     * Verifica si se debe guardar una copia.
     *
     * @return true si se debe guardar una copia, false en caso contrario
     */
    public static Boolean getSaveCopy() {
        return Boolean.parseBoolean(properties.getProperty("savecopy", "false"));
    }
}
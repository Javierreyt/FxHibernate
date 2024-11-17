package org.hibernateproject.pruebajavafxconhibernatehello.utils;

import java.io.*;
import java.util.Base64;
import java.util.Properties;

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

    // Método genérico para guardar cualquier objeto serializable
    public static <T extends Serializable> void saveObject(String key, T object) {
        try {
            // Serializar el objeto
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();

            // Codificar en base64 y guardar en las propiedades
            String objectBase64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            properties.setProperty(key, objectBase64);
            saveProperties();
        } catch (IOException e) {
            System.out.println("Error al serializar el objeto: " + e.getMessage());
        }
    }

    // Método genérico para cargar cualquier objeto serializable
    @SuppressWarnings("unchecked")
    public static <T> T loadObject(String key, Class<T> clazz) {
        try {
            String objectBase64 = properties.getProperty(key);
            if (objectBase64 != null) {
                // Decodificar de base64 a array de bytes
                byte[] objectBytes = Base64.getDecoder().decode(objectBase64);

                // Deserializar el array de bytes en un objeto
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (T) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar el objeto: " + e.getMessage());
        }
        return null;
    }

    // Métodos para obtener los valores de cada campo
    public static String getUserNombreUsuario() {
        return properties.getProperty("user.nombre_usuario");
    }

    public static String getUserContraseña() {
        return properties.getProperty("user.contrasena");
    }

    public static boolean isRecordarUsuario() {
        return Boolean.parseBoolean(properties.getProperty("recordarUsuario", "false"));
    }

    public static boolean isInfo() {
        return Boolean.parseBoolean(properties.getProperty("info", "false"));
    }

    // Métodos para establecer los valores y guardar en el archivo
    public static void setUserNombreUsuario(String nombreUsuario) {
        properties.setProperty("user.nombre_usuario", nombreUsuario);
        saveProperties();
    }

    public static void setUserContraseña(String contraseña) {
        properties.setProperty("user.contrasena", contraseña);
        saveProperties();
    }

    public static void setRecordarUsuario(boolean recordarUsuario) {
        properties.setProperty("recordarUsuario", Boolean.toString(recordarUsuario));
        saveProperties();
    }

    public static void setInfo(boolean info) {
        properties.setProperty("info", Boolean.toString(info));
        saveProperties();
    }

    // Guardar los cambios en el archivo
    private static void saveProperties() {
        try (FileOutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, "Propiedades de la aplicación");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo de propiedades: " + e.getMessage());
        }
    }

    public static void setSaveCopy(boolean saveCopy) {
        properties.setProperty("savecopy", Boolean.toString(saveCopy));
        saveProperties();
    }

    public static Boolean getSaveCopy(){
        return Boolean.parseBoolean(properties.getProperty("savecopy", "false"));
    }
}


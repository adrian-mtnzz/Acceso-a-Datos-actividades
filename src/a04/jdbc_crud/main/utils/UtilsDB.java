package a04.jdbc_crud.main.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Clase que maneja las credenciales de la BDD
public class UtilsDB {
    private static final String PROPERTIES_FILE = "a04/secrets/app.properties";

    // Constante donde se almacenaran las credenciales
    private final Properties PROPERTIES;

    public UtilsDB() {
        PROPERTIES = new Properties();

        // Abre un stream de entrada donde se cargan las propiedas del fichero app.properties
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {

            if (inStream == null) {
                System.err.println("Error: No se pudo encontrar " + PROPERTIES_FILE + " en el classpath\n");
                throw new IOException();
            }
            // Almacena en la variable PROPERTIES las propiedades cargadas en el stream
            PROPERTIES.load(inStream);

        } catch (IOException ex) {
            throw new RuntimeException("Error al cargar el archivo de propiedades");
        }
    }

    public String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    // Metodo para limpiar las propiedades de la memoria al finalizar la conexion
    public void clearProperties() {
        PROPERTIES.clear();
    }
}

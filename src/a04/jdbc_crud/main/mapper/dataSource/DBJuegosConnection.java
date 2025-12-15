package a04.jdbc_crud.main.mapper.dataSource;

import a04.jdbc_crud.main.utils.UtilsDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase encargada de establecer la conexion a la BDD
public class DBJuegosConnection {
    private Connection conn = null;
    private UtilsDB config = null;

    // Metodo interno que realiza la conexion
    private void connect() {

        // Crea un objeto UtilsDB para cargar las propiedades
        config = new UtilsDB();

        // Mapeo de las credenciales en variables
        String url = config.getProperties("db.url");
        String user = config.getProperties("db.user");
        String password = config.getProperties("db.password");

        // Carga el driver H2 y establece la conexion
        try {
            if (conn == null) {
                Class.forName("org.h2.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Conexion establecida correctamente\n");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Error al conectar con la base de datos\n"+ ex.getMessage());
            System.exit(-1);
        }
    }

    // Metodo para cerrar la conexion y limpiar las propiedades de la memoria
    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                config.clearProperties();
                System.out.println("Conexion cerrada correctamente\n");
                conn = null;
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexion con la base de datos\n"+ex.getMessage());
        }
    }

    public Connection getConnection() {
        if (conn == null) connect();
        return conn;
    }
}

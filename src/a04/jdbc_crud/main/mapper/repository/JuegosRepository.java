package a04.jdbc_crud.main.mapper.repository;

import a04.jdbc_crud.main.mapper.dataSource.DBJuegosConnection;
import a04.jdbc_crud.main.model.Juego;

import java.sql.*;
import java.util.ArrayList;


// Clase encargada de realizar las operaciones CRUD utilizando JDBC
public class JuegosRepository implements AutoCloseable {

    private final DBJuegosConnection dbConnection;
    private final Connection conn;
    private ArrayList<Juego> gamesList = new ArrayList<>();

    // El constructor inicializa la conexion mediante la clase DBJuegosConnection
    public JuegosRepository() {
        dbConnection = new DBJuegosConnection();
        conn = dbConnection.getConnection();
    }

    // Metodo para actualizar la lista de juegos local
    // tras cada operacion de insercion, actualizacion o eliminacion
    public void cacheGamesList() {
        gamesList.clear();
        getAllGames();
    }

    // Metodo para la insercion de un nuevo juego
    public void addGame(Juego game) {
        String sql = "INSERT INTO JUEGO (NOMBRE, GENERO, PUNTUACION) VALUES (?,?,?);";

        // Prepara la sentencia sql asignadole valor a las bind variables
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // NOMBRE
            pst.setString(1, game.getName());
            // GENERO
            pst.setString(2, game.getGenre());
            // PUNTUACION
            pst.setDouble(3, game.getRating());

            // Ejecuta el prepared statement
            if (pst.executeUpdate() != 1) throw new SQLException();

            System.out.println("Juego insertado correctamente: " + game.getName() + "\n\n");

            // Actualiza la lista de juegos local
            cacheGamesList();

        } catch (SQLException ex) {
            System.err.println("Error al realizar la insercion del juego\n" + ex.getMessage());
        }
    }


    // Metodo para realizar la consulta de un juego por nombre
    public Juego getGameByName(String name) {
        Juego game = null;
        String sql = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO WHERE NOMBRE = ?;";

        // Prepara la sentencia sql asignandole valor a las bind variables
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // NOMBRE
            pst.setString(1, name);

            // Ejecuta el prepared statement y crea un result set
            // para mapear el resultado en un nuevo objeto de la clase Juego
            try (ResultSet rs = pst.executeQuery()) {

                // Mueve el cursor a la primera posicion del resultado
                rs.next();

                // Mapeo de atributos
                int id = rs.getInt("ID");
                String genre = rs.getString("GENERO");
                double rating = rs.getDouble("PUNTUACION");

                // Creacion local del juego obtenido tras la consulta
                game = new Juego(id, name, genre, rating);
            }

        }catch (SQLException e) {
            System.err.println("No se ha enontrado el juego: " + name + "\n\n");
        }

        // Devuelve el juego consultado
        return game;
    }

    // Metodo para consultar todos los juegos
    public ArrayList<Juego> getAllGames() {
        String sql = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO;";

        // Ejecuta la sentencia sql e itera los resultados con result set
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {


            // Iteracion de los resultados y mapeo en objetos de la clase Juego
            while (rs.next()) {

                // Mapeo de atributos
                int id = rs.getInt("ID");
                String name = rs.getString("NOMBRE");
                String genre = rs.getString("GENERO");
                double rating = rs.getDouble("PUNTUACION");

                // Agregar el juego a la lista de juegos local
                gamesList.add(new Juego(id, name, genre, rating));
            }

        } catch (SQLException ex) {
            System.err.println("Error al consultar los juegos\n" + ex.getMessage());
        }

        // Devuelve la lista de juegos
        return gamesList;
    }


    // Metodo para actualizar la puntuacion de un juego
    public void updateRanking(Juego game, double rating) {
        String sql = "UPDATE JUEGO SET PUNTUACION = ? WHERE ID = ?;";

        // Prepara la sentencia sql asignandole valor a las bind variables
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // PUNTUACION actualizada
            pst.setDouble(1, rating);
            // ID del juego a actualizar
            pst.setInt(2, game.getId());

            // Comprueba que solo se haya actualizado un juego
            if (pst.executeUpdate() != 1) throw new SQLException();

            System.out.println(
                    "La puntuacion del juego " + game.getName() +
                    " se ha actualizado correctamente a "+game.getRating() + "\n"
            );

            // Actualiza la lista de juegos local
            cacheGamesList();

        } catch (SQLException ex) {
            System.err.println("Error al actualizar el juego\n" + ex.getMessage());
        }
    }


    // Metodo para eliminar un juego por el nombre
    public void deleteGame(String name) {
        String sql = "DELETE FROM JUEGO WHERE NOMBRE = ?;";

        // Iteracion de la lista de juegos local
        for (Juego game : gamesList) {

            // Comprueba si existe un juego con ese nombre en la lista local de juegos
            if (name.equals(game.getName())) {

                // Prepara la sentencia sql asignandole el valor a la bind variable del nombre
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, name);

                // Ejecuta el prepared statement eliminando el registro de ese juego
                pst.executeUpdate();
                System.out.println("Juego " + name + " se ha eliminado correctamente\n");

                // Actualiza la lista de juegos local
                cacheGamesList();

                } catch (SQLException e) {
                    System.err.println("Error al eliminar el juego\n");
                }

                // Detiene la iteracion de la lista
                return;
            }
        }
    }

    // Metodo para eliminar el registro en la BDD de un juego de la lista local
    public void deleteGame(Juego game) {
        String sql = "DELETE FROM JUEGO WHERE ID = ?;";

        // Prepara la sentencia sql asignandole el valor a la bind variable del id
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, game.getId());

            // Ejecuta el prepared statement
            pst.executeUpdate();
            System.out.println("El juego con ID:" + game.getId() + " y nombre: " + game.getName() + " se ha eliminado correctamente\n");

            // Actualiza la lista de juegos local
            cacheGamesList();

        } catch (SQLException | NullPointerException ex) {
            System.err.println("Error al eliminar el juego\n");
        }
    }

    // Metodo para cerrar la conexion y limpiar las propiedades automaticamente
    // dentro de los bloques try with resources
    @Override
    public void close() {
        dbConnection.disconnect();
    }
}

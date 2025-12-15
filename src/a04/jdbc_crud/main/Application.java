package a04.jdbc_crud.main;

import a04.jdbc_crud.main.mapper.repository.JuegosRepository;
import a04.jdbc_crud.main.model.Juego;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {

        // Inicializacion del repositorio
        try (JuegosRepository repo = new JuegosRepository()) {

            // 1. Consulte y muestre todos los juegos de la tabla JUEGO
            ArrayList<Juego> gameList = repo.getAllGames();
            System.out.println("\n\n\n\u001b[34mLista inicial de juegos en la Base de Datos H2:\u001b[0m\n\n");
            printGames(gameList);

            // 2. Inserte un nuevo juego
            repo.addGame(new Juego("Elden Ring", "Accion",9.5));

            // 3. Vuelva a consultar los datos para comprobar la inserción
            System.out.println("\n\n\n\u001b[34mLista de juegos tras la insercion:\u001b[0m\n\n");
            printGames(gameList);

            // 4. Actualice la puntuación de ese nuevo juego
            repo.updateRanking(repo.getGameByName("Elden Ring"), 10);

            // 5. Vuelva a consultar para comprobar la actualización
            System.out.println("\n\n\n\u001b[34mLista de juegos tras actualizar el registro:\u001b[0m\n\n");
            printGames(gameList);

            // 6. Elimine algún juego (por nombre o por id), adios fifa, no eras tan bueno...
            repo.deleteGame(repo.getGameByName("FIFA"));

            // 7. Haga una última consulta para ver el estado final de la tabla
            System.out.println("\n\n\n\u001b[34mLista de juegos tras eliminar el juego:\u001b[0m\n\n");
            printGames(gameList);
        }
    }

    // Metodo para mostrar los juegos en lista de juegos local
    public static void printGames(ArrayList<Juego> gameList) {
        for (Juego game : gameList) {
            System.out.println(game);
        }
    }
}

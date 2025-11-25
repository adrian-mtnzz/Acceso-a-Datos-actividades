package a03.JSON.java;

import a03.JSON.java.controller.PeliculasControllerImpl;
import a03.JSON.java.model.Pelicula;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        System.out.println("\n\n\u001b[34m" +
                "                                        \n" +
                "██ ▄████▄      ██ ▄█████ ▄████▄ ███  ██ \n" +
                "██ ██  ██      ██ ▀▀▀▄▄▄ ██  ██ ██ ▀▄██ \n" +
                "██ ▀████▀   ████▀ █████▀ ▀████▀ ██   ██ \n" +
                "\u001b[0m\n\n                             ");
        PeliculasControllerImpl controller  = new PeliculasControllerImpl();

        // Generacion de una lista de objetos de la clase Pelicula
        List<Pelicula> listaPeliculas = controller.generarListaPeliculas();

        // Generacion del contenido del JSON
        JSONObject JSON = controller.generarJSON(listaPeliculas);

        // Escritura del contenido en un archivo dentro de la carpeta resources
        File JSONFile = controller.escribirJSON(JSON);

        // Lectura del JSON generado por salida estandar de la pantalla.
        controller.leerJSON(JSONFile);
    }
}

package a03.JSON.java.controller;

import a03.JSON.java.model.Pelicula;
import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeliculasControllerImpl implements PeliculasController {

    @Override
    public List<Pelicula> generarListaPeliculas() {

        // Generar una lista de peliculas de ejemplo
        return Arrays.asList(
                new Pelicula("Pelicula 1", "Director 1", 1992),
                new Pelicula("Pelicula 2", "Director 2", 2004),
                new Pelicula("Pelicula 3", "Director 3", 2010)
        );
    }

    @Override
    public JSONObject generarJSON(List<Pelicula> listaPeliculas) {
        // Creacion del objeto JSON
        JSONObject root = new JSONObject();

        // Creacion JSONArray para lista de peliculas
        JSONArray arrPeliculas = new JSONArray();

        // Iteracion lista peliculas
        for (Pelicula p : listaPeliculas) {

            // Objeto JSON para cada pelicula
            JSONObject pelicula = new JSONObject();

            // Asignacion de atributos
            pelicula.put("Titulo", p.getTitulo());
            pelicula.put("Director", p.getDirector());
            pelicula.put("Fecha", p.getAnio());

            // Agregar el JSONObject de la pelicula alJSONArray
            arrPeliculas.put(pelicula);
        }

        // Agregar campo clave valor al Objeto Raiz con el array de peliculas
        root.put("Peliculas",arrPeliculas);

        return root;
    }

    @Override
    public File escribirJSON(JSONObject root) {

        // Creacion de archivo peliculas.json
        File JSONFile = new File("src/a03/JSON/resources/peliculas.json");
        try (FileWriter fw = new FileWriter(JSONFile)) {

            // Escritura de los datos del objeto JSON raiz en el archivo
            fw.write(root.toString(4));
            System.out.println("\u001b[34mJSON escrito en: \u001b[0m" + JSONFile.getPath());

        } catch (IOException ex) {
            System.err.println("Error al escribir el documento JSON" + ex.getMessage());
        }
        return JSONFile;
    }

    @Override
    public void leerJSON(File JSONfile) {
        // Lista donde se almacenaran las peliculas obtenidas del JSON
        List<Pelicula> listaPeliculas = new ArrayList<>();

        // Creacion del FileReader para leer el JSON
        try (FileReader reader = new FileReader(JSONfile.getPath())) {

            // Creacion del tokener para convertir el texto a objetos JSON
            JSONTokener tokener = new JSONTokener(reader);

            // Recreacion del objeto JSON raiz
            JSONObject root = new JSONObject(tokener);

            // Obtener el JSONArray de las peliculas
            JSONArray arrPeliculas = root.getJSONArray("Peliculas");

            System.out.println("\n\n\u001b[32mListando peliculas obtenidas del JSON:\u001b[0m\n");

            // Iterar JSONArray de peliculas
            for (Object p : arrPeliculas) {

                // Creacion de JSON para cada pelicula
                JSONObject peliculaObj = (JSONObject) p;

                // Creacion de un objeto Pelicula extrayendo atributos del JSONObject
                Pelicula pelicula = new Pelicula(
                        peliculaObj.getString("Titulo"),
                        peliculaObj.getString("Director"),
                        peliculaObj.getInt("Fecha")
                );

                // Agregar a la lista de peliculas
                listaPeliculas.add(pelicula);

                // Leer por pantalla los datos de la pelicula agregada
                System.out.println(pelicula);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado:\t" + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo \n" + e.getMessage());
        }
    }
}

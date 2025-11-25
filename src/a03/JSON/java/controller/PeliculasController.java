package a03.JSON.java.controller;

import a03.JSON.java.model.Pelicula;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public interface PeliculasController {
    public List<Pelicula> generarListaPeliculas();
    public JSONObject generarJSON(List<Pelicula> listaPeliculas);
    public File escribirJSON(JSONObject root);
    public void leerJSON(File JSONFile);
}

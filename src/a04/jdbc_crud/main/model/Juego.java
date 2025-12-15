package a04.jdbc_crud.main.model;

public class Juego {
    private int id;
    private final String name;
    private final String genre;
    private final double rating;

    // Constructor utilizado para la creacion e insercion de Juegos
    public Juego(String name, String genre, double rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    // Constructor utilizado para mapear juegos obtenidos de la BDD
    public Juego(int id, String name, String genre, double rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return  "\u001b[32m"    +
                "ID: "          + id             + "\n\n" +
                "\t\t\t"        + "Nombre: "     + name   +
                "\t\t\t"        + "Genero: "     + genre  +
                "\t\t\t"        + "Puntuacion: " + rating +
                "\n\u001b[35m"  + "-".repeat(100)   +
                "\u001b[0m";
    }
}

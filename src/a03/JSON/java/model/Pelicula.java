package a03.JSON.java.model;

public class Pelicula {
    private String titulo;
    private String director;
    private int anio;

    public Pelicula(String titulo, String director, int anio) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return  "\n\u001b[35m" +
                "-----------------------------------------------------------------------------------" +
                "\n\u001b[36mPELICULA:\u001b[32m\t\t" + getTitulo()   + "\t" +
                "\t\u001b[36mDIRECTOR:\u001b[32m\t"   + getDirector() + "\t" +
                "\t\u001b[36mFECHA:\u001b[32m\t"      + getAnio()     +
                "\n\u001b[35m" +
                "-----------------------------------------------------------------------------------" +
                "\u001b[0m";
    }
}

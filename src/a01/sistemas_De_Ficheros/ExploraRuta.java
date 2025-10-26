package a01.sistemas_De_Ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/* ATENCION
 *
 * El entorno utilizado para el desarrollo de esta actividad utiliza un sistema que corre
 * bajo el sistema operativo ARCH Linux por lo que se tendra en cuenta la compatibilidad
 * tanto para Windows como para Linux a la hora de detectar rutas
 *
 */

public class ExploraRuta {

    private static final Logger log = Logger.getLogger(ExploraRuta.class.getName());
    public Scanner scanner = new Scanner(System.in);
    public String ruta;
    public File file;

    public void introducirRuta() {
        boolean hasSeparators = false;
        System.out.println("\nIntroduce una ruta: \n");

        do {
            ruta = scanner.nextLine().replaceAll("\\s", "");
            if(ruta.contains("/") || ruta.contains("\\")) {
                hasSeparators = true;
            } else {
                System.out.println();
                log.info("Ruta no valida introduce una ruta con el formato correcto: \n");
            }
        } while(!hasSeparators);
    }

    public void revisarRuta() {
        file = new File(ruta);

        if (file.exists()) {
            mostrarDatosFichero(file);
            mostrarDatosDirectorio(file);

        } else {
            System.out.println("\nNo existe la ruta introducida en tu sistema");
            introducirRuta();
            revisarRuta();
        }
    }

    public void mostrarDatosFichero(File file) {
        if (file.isFile()) System.out.println("Archivo encontrado\n\n" +
                "\u001b[32m"+"Nombre      : " + "\u001b[0m" + file.getName() + "\n" +
                "\u001b[32m"+"Tamaño      : " + "\u001b[0m" + file.length() + " " + "bytes" + "\n" +
                "\u001b[32m"+"Permisos rw : " + "\u001b[0m" + "Lectura " + file.canRead() + "  ||  " + "Escritura " + file.canWrite());
    }

    public void mostrarDatosDirectorio(File file) {
        File[] elementosRuta;
        ArrayList<File> directorios;
        ArrayList<File> archivos;

        if (file.isDirectory()) {
            elementosRuta = file.listFiles();
            directorios = new ArrayList<>();
            archivos = new ArrayList<>();

            System.out.println("Carpeta encontrada: "+file.getName()+"\n");
            assert elementosRuta != null;
            System.out.println(elementosRuta.length + " Elementos dentro de la carpeta: ");
            for (File e : elementosRuta) {
                boolean b = e.isDirectory()? directorios.add(e) : archivos.add(e);
            }
            System.out.println();
            for (File d : directorios) {
                System.out.println("\u001b[34m"+"    Carpeta:    " + "\u001b[0m" + d.getName());
            }
            for (File f : archivos) {
                System.out.println("\u001b[32m"+"    Fichero:    " + "\u001b[0m" + f.getName());
            }
        }
    }

    static void main() {
        System.out.println("\u001b[36m" + "\n\n" +
                "___________              .__                              __________        __          \n" +
                "\\_   _____/__  _________ |  |   ________________ _______  \\______   \\__ ___/  |______   \n" +
                " |    __)_\\  \\/  /\\____ \\|  |  /  _ \\_  __ \\__  \\\\_  __ \\  |       _/  |  \\   __\\__  \\  \n" +
                " |        \\>    < |  |_> >  |_(  <_> )  | \\// __ \\|  | \\/  |    |   \\  |  /|  |  / __ \\_\n" +
                "/_______  /__/\\_ \\|   __/|____/\\____/|__|  (____  /__|     |____|_  /____/ |__| (____  /\n" +
                "        \\/      \\/|__|                          \\/                \\/                 \\/ "
                + "\u001b[0m" +"\n\n"
        );

        ExploraRuta ruta = new ExploraRuta();
        ruta.introducirRuta();
        ruta.revisarRuta();

    }
}


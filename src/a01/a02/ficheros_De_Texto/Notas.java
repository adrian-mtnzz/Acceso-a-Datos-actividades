package a01.a02.ficheros_De_Texto;


/* ATENCION
 *
 * El entorno utilizado para el desarrollo de esta actividad utiliza un sistema que corre
 * bajo el sistema operativo ARCH Linux por lo que se tendra en cuenta la compatibilidad
 * tanto para Windows como para Linux a la hora de detectar rutas
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Notas {
    private static final Logger log = Logger.getLogger(Notas.class.getName());
    public static Scanner scanner = new Scanner(System.in);
    public static File file;
    ArrayList<String> lineas;


    public void crearTxtFile() {
        file = new File("src/a02/ficheros_De_Texto/prueba.txt");

        try {
            boolean b = file.createNewFile();
            System.out.println(b
                    ?"\u001b[34m\nSe ha creado el fichero de texto: " + file.getName() + " en la ruta: " + file.getAbsolutePath() + "\u001b[0m\n"
                    :"\u001b[34m\nUtilizando el fichero " + file.getName() + "\u001b[0m\n");
        } catch(IOException ex) {log.severe(ex.getMessage());}
    }

    public void pedirLineas() {
        lineas = new ArrayList<>();
        int counter = 0;

        System.out.println("\nIngresa tres lineas para que sean escritas en el fichero: \n");
        scanner.nextLine();
        while (counter != 3) {
            counter++;
            lineas.add(scanner.nextLine());
        }
        escribirLineas();
    }

    public void escribirLineas(){

        if (file.exists()) try(FileWriter writer = new FileWriter(file, true)) {

            for(String linea : lineas) {
                writer.write(linea+System.lineSeparator());
            }
            writer.close();
            lineas.clear();

        } catch (IOException ex) {
            log.severe("\nError al escribir en el archivo\n"+ex.getMessage());

        } else {
            System.out.println();
            log.info("Archivo no encontrado, creando un nuevo archivo y escribiendo las lineas en el");
            crearTxtFile();
            escribirLineas();
        }
    }

    public void leerLineas(){
        String linea;
        int counter = 0;

        if (file.exists()) try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println();
            while((linea = br.readLine()) != null) {
                counter++;
                System.out.println(counter + ": " + linea);
            }
        } catch (FileNotFoundException ex) {
            log.severe("\nError archivo no encontrado\n"+ ex.getMessage());
        } catch (IOException e) {
            log.severe("\nError al intentar leer el archivo\n" + e.getMessage());
        } else {
            log.info("El archivo no existe, creando uno nuevo ...");
            crearTxtFile();
            leerLineas();
        }
    }

    public void eliminarArchivo() {
        if (file.delete()){
            System.out.println("\u001b[34mArchivo " + file.getName() + " eliminado correctamente\u001b[0m");
        } else {
            log.info("Error al eliminar el archivo");
        }
    }

    public static void main(String[] args) {
        Notas notas = new Notas();

        System.out.println(
                "\n\n\u001b[36m _______          __           _________\n" +
                " \\      \\   _____/  |______   /   _____/\n" +
                " /   |   \\ /  _ \\   __\\__  \\  \\_____  \\ \n" +
                "/    |    (  <_> )  |  / __ \\_/        \\\n" +
                "\\____|__  /\\____/|__| (____  /_______  /\n" +
                "        \\/                 \\/        \\/ \u001b[0m\n");

        notas.crearTxtFile();

        while (true) {
            System.out.println("""
                    \n\u001b[32mSelecciona la opcion que quieres realizar:\u001b[0m 
                        
                        \u001b[32m1.\u001b[0m Introducir 3 lineas y escribirlas en el fichero
                        \u001b[32m2.\u001b[0m Leer el contenido del fichero
                        \u001b[32m3.\u001b[0m Eliminar el fichero
                        \u001b[32m4.\u001b[0m Cerrar el programa     
                    """);
            String opcion = scanner.next();
            switch (opcion) {
                case "1":
                    notas.pedirLineas();
                    break;
                case "2":
                    notas.leerLineas();
                    break;
                case "3":
                    notas.eliminarArchivo();
                    break;
                case "4":
                    System.out.println("\u001b[32m\n\nCerrando el programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida, introduce un numero correspondiente a una de las opciones.");
            }
        }
    }

}

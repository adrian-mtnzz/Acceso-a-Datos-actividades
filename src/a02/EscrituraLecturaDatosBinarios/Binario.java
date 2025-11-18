package a02.EscrituraLecturaDatosBinarios;

import java.io.*;

public class Binario {
    public static void main(String[] args) {

        System.out.println("\n\n\n\u001b[36m" +
                "__________.__                    .__        \n" +
                "\\______   \\__| ____ _____ _______|__| ____  \n" +
                " |    |  _/  |/    \\\\__  \\\\_  __ \\  |/  _ \\ \n" +
                " |    |   \\  |   |  \\/ __ \\|  | \\/  (  <_> )\n" +
                " |______  /__|___|  (____  /__|  |__|\\____/ \n" +
                "        \\/        \\/     \\/                 " + "\u001b[0m"
        );

        String rutaFichero = "src/a02/EscrituraLecturaDatosBinarios/data.bin";


        // Creacion del fichero y escritura de los datos binarios
        try (DataOutputStream outStream = new DataOutputStream(new FileOutputStream(rutaFichero))) {
            outStream.writeInt(3050064);
            outStream.writeUTF("String en fichero");
            outStream.writeDouble(0.0012d);

            System.out.println("\n\n\n\u001b[32mDatos guardados correctamente en la ruta: " + rutaFichero + "\u001b[0m\n\n");

        } catch (FileNotFoundException ex) {
            System.out.println("Error al abrir el fichero: " + ex.getMessage());

        } catch (IOException ex) {
            System.out.println("Error al escribir los datos en el archivo: " + ex.getMessage());
        }


        // Lectura de los datos del fichero e impresion por pantalla
        System.out.println("\u001b[36mLeyendo datos del fichero...\u001b[0m\n");
        try (DataInputStream inStream = new DataInputStream(new FileInputStream(rutaFichero))) {
            int i = inStream.readInt();
            String s = inStream.readUTF();
            double d = inStream.readDouble();

            System.out.println(
                    "\n\t\u001b[33mDatos leidos:" + "\n\n" +
                    "\t\t\u001b[35mint = \u001b[0m" + i    + "\n" +
                    "\t\t\u001b[35mString = \u001b[0m" + "\"" + s + "\"" + "\n" +
                    "\t\t\u001b[35mdouble = \u001b[0m" + d + "\n\n\n"
                    );

        } catch (FileNotFoundException ex) {
            System.out.println("Error al acceder al fichero: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al leer los datos del fichero: " + ex.getMessage());
        }

    }
}

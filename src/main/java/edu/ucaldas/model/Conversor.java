package edu.ucaldas.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Conversor realiza la conversión de datos desde un archivo CSV a un archivo JSON.
 */
public class Conversor {

    /**
     * Lee datos desde un archivo CSV y los almacena en una lista de objetos Estudiante.
     *
     * @param rutaArchivo Ruta del archivo CSV que se va a leer.
     * @return Lista de objetos Estudiante con los datos del archivo CSV.
     */
    public List<Estudiante> leerDatosDesdeCSV(String rutaArchivo) {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] campos = line.split(",");

                if (campos.length == 3) {
                    String id = campos[0].trim(); // Elimina espacios en blanco
                    String nombre = campos[1].trim();
                    String apellido = campos[2].trim().replace("\"", ""); // Elimina comillas dobles

                    estudiantes.add(new Estudiante(id, nombre, apellido));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            System.exit(1); // Salir con código de error
        }

        return estudiantes;
    }

    /**
     * Convierte los datos desde un archivo CSV a un archivo JSON.
     *
     * @param rutaArchivo Ruta del archivo CSV que se va a convertir.
     */
    public void convertirCSVaJSON(String rutaArchivo) {
        List<Estudiante> estudiantes = leerDatosDesdeCSV(rutaArchivo);
        String rutaJson = rutaArchivo.replaceFirst("[.][^.]+$", ".json"); // Calcular la ruta del archivo JSON
        convertirYGuardarComoJSON(estudiantes, rutaJson);
    }

    /**
     * Convierte una lista de objetos Estudiante a un formato JSON y guarda los datos en un archivo.
     *
     * @param estudiantes  Lista de objetos Estudiante.
     * @param rutaJson Ruta del archivo JSON en el que se guardarán los datos.
     */
    public void convertirYGuardarComoJSON(List<Estudiante> estudiantes, String rutaJson) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante estudiante = estudiantes.get(i);
            jsonBuilder.append("    {\n");
            jsonBuilder.append("        \"id\": \"" + estudiante.getId().replace("\"", "") + "\",\n");
            jsonBuilder.append("        \"nombre\": \"" + estudiante.getNombre() + "\",\n");
            jsonBuilder.append("        \"apellido\": \"" + estudiante.getApellido() + "\"\n");
            jsonBuilder.append("    }");

            if (i < estudiantes.size() - 1) {
                jsonBuilder.append(",");
            }

            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]\n");

        try (FileWriter fileWriter = new FileWriter(rutaJson)) {
            fileWriter.write(jsonBuilder.toString());
            System.out.println("Datos convertidos y guardados en " + rutaJson);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo JSON: " + e.getMessage());
            System.exit(1); // Salir con código de error
        }
    }
}

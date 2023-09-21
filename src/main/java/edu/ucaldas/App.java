package edu.ucaldas;

import java.util.Scanner;

import edu.ucaldas.model.Conversor;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al conversor de CSV a JSON.");
        System.out.print("Ingrese la ruta del archivo CSV: ");
        String rutaArchivo = scanner.nextLine();

        scanner.close(); // Cierra el scanner cuando ya no se necesita

        Conversor converter = new Conversor();
        converter.convertirCSVaJSON(rutaArchivo);
    }
}


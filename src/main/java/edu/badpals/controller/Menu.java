package edu.badpals.controller;

import edu.badpals.view.View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static void logicaMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion = -1;
            do {
                try {
                    View.OpcionesMenu();
                    System.out.print("Selecciona una opción: ");
                    opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            Controlador.crearPista();
                            break;
                        case 2:
                            Controlador.modificarPista(scanner);
                            break;
                        case 3:
                            Controlador.eliminarPista();
                            break;
                        case 4:
                            Controlador.listarReservas();
                            break;
                        case 5:
                            Controlador.mostrarPistaMasRentable();
                            break;
                        case 6:
                            Controlador.listarReservasSinIva();
                            break;
                        case 7:
                            View.pistasPorCalle(scanner);
                            break;
                        case 8:
                            scanner.nextLine();
                            View.preciosReservas(scanner);
                            break;
                        case 9:
                            Controlador.mostrarMetadatosBBDD(scanner);
                            break;
                        case 0:
                            View.messageSalirMenu();
                            break;
                        default:
                            View.messageErrorSeleccionSwich();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debes introducir un número válido.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error: " + e.getMessage());
                }
            } while (opcion != 0);
        }
    }
}

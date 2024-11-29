package edu.badpals.controller;


import edu.badpals.model.Operacions;
import edu.badpals.model.Pista;
import edu.badpals.model.Polideportivos;
import edu.badpals.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {

    public static void logicaMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            View.OpcionesMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.print("ID de la pista: ");
                    int idPista = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("codigo de la pista: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Introduce operativa: ");
                    boolean operativa = scanner.nextBoolean();
                    System.out.print("ID del polideportivo: ");
                    int id_polideportivo = scanner.nextInt();
                    Pista newPista = new Pista(idPista,codigo,operativa,id_polideportivo);
                    Operacions.addPista(newPista);
                    break;
                case 2:
                    System.out.print("ID de la pista a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    Operacions.deletePista(idEliminar);
                    break;
                case 3:
                    Pista updatePista= new Pista(2,"3456",true,3);
                    Operacions.updatePista(updatePista);
                    break;
                case 4:
                    View.ensenarInforBBDD(Operacions.getInformacionConexion("polideportivos", "pista"));
                    break;
                case 5:
                    ArrayList<String> datosPista = Operacions.getPistaMaxGenerado();
                    System.out.println("Pista que mas generó:");
                    System.out.println("ID Pista: " + datosPista.get(0));
                    System.out.println("Número de reservas: " + datosPista.get(1));
                    System.out.println("Suma total: " + datosPista.get(2));
                    break;
                case 6:
                    View.precioPistas(scanner);
                    break;
                case 7:
                    View.listadoReservasSinIva();
                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
}

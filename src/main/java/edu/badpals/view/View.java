package edu.badpals.view;

import edu.badpals.model.Operacions;
import edu.badpals.model.Reservas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public static void OpcionesMenu() {
        System.out.println("Opciones:\n");
        System.out.println("1. Crear nueva pista");
        System.out.println("2. Eliminar la pista");
        System.out.println("3. Modificar la pista");
        System.out.println("4. Metadatos");


    }
    public static void ensenarInforBBDD(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println("Columna: " + rs.getString("COLUMN_NAME") + ", Tipo de datos: " + rs.getString("TYPE_NAME") +
                        ", Tamaño: " + rs.getInt("COLUMN_SIZE") + ", Admite nulos: " + rs.getString("IS_NULLABLE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al enseñar la información de la bbdd");
        }
    }

    public static void listadoReservasSinIva() {
        ArrayList<Reservas> reservaiva = Operacions.getListadoReservasSinIva();
        System.out.println("---------- Listado de Reservas sin IVA ----------\n");
        for (Reservas reservas : reservaiva) {
            System.out.println(reservas);
        }

    }
    public static void precioPistas(Scanner scanner) {
        System.out.print("Codigo pista: ");
        scanner.nextLine();
        String codigo = scanner.nextLine();
        Operacions.getListadoPreciosReservas(codigo);

    }
}

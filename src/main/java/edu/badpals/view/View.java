package edu.badpals.view;

import edu.badpals.baseDatos.Operacions;
import edu.badpals.baseDatos.Pista;
import edu.badpals.baseDatos.Polideportivos;
import edu.badpals.baseDatos.Reservas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {

    public static void OpcionesMenu() {
        System.out.println("*".repeat(55));
        System.out.println("*"+" ".repeat(18) + "MENÚ PRINCIPAL"+" ".repeat(21)+"*");
        System.out.println("*".repeat(55));
        System.out.println("|  1. Crear nueva pista                               |");
        System.out.println("|  2. Modificar la pista                              |");
        System.out.println("|  3. Eliminar la pista                               |");
        System.out.println("|  4. Listar las reservas                             |");
        System.out.println("|  5. Mostrar pista que más dinero ha generado        |");
        System.out.println("|  6. Mostrar reservas sin IVA                        |");
        System.out.println("|  7. Número de pistas operativas por calle           |");
        System.out.println("|  8. Precios de las reservas asociadas a una pista   |");
        System.out.println("|  9. Mostrar información de las tablas de la base    |");
        System.out.println("|  0. Salir                                           |");
        System.out.println("=".repeat(55));
    }

    public static void mostrarListasReservas(List<Reservas> reservasList) {
        for (Reservas reserva : reservasList) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID Reserva: ").append(reserva.getIdReservas())
                    .append(" | Precio: ").append(reserva.getPrecio());
            if (reserva.getPista() != null) {
                Pista pista = reserva.getPista();
                sb.append(" | Pista ID: ").append(pista.getIdPista())
                        .append(" | Código Pista: ").append(pista.getCodigo())
                        .append(" | Operativa: ").append(pista.isOperativa());

                if (pista.getPolideportivos() != null) {
                    Polideportivos polideportivo = pista.getPolideportivos();
                    sb.append(" | Polideportivo ID: ").append(polideportivo.getIdPolideportivos())
                            .append(" | Nombre Polideportivo: ").append(polideportivo.getNombre())
                            .append(" | Dirección Polideportivo: ").append(polideportivo.getDireccion());
                } else {
                    sb.append(" | Polideportivo: null");
                }
            } else {
                sb.append(" | Pista: null");
            }
            System.out.println(sb);
        }
    }

    //Yo tambien preferiría usar este para visualizarlas

    /*public static void mostrarListasReservas(List<Reservas> reservasList) {
        for (Reservas reserva : reservasList) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID Reserva: ").append(reserva.getIdReservas())
                    .append(" | Precio: ").append(reserva.getPrecio());
            if (reserva.getPista() != null) {
                sb.append(" | Pista: ").append(reserva.getPista().getCodigo());
                if (reserva.getPista().getPolideportivos() != null) {
                    sb.append(" | Polideportivo: ").append(reserva.getPista().getPolideportivos().getNombre());
                } else {
                    sb.append(" | Polideportivo: null");
                }
            } else {
                sb.append(" | Pista: null");
            }

            System.out.println(sb);
        }
    }*/


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
    public static String nombreMetadatos(String x, Scanner scanner) {
        System.out.println(x);
        scanner.nextLine();
        String nombreTabla = scanner.nextLine();
        return nombreTabla;
    }

    public static void preciosReservas(Scanner scanner) {
        System.out.println("Introduce el código de la pista:");
        String codigoPista = scanner.nextLine();
        List<Double> precios = Operacions.getListadoPreciosReservas(codigoPista);

        if (!precios.isEmpty()) {
            System.out.println("Precios de las reservas para el código de pista '" + codigoPista + "':");
            for (Double precio : precios) {
                System.out.println("Precio: " + precio);
            }
        } else {
            System.out.println("No se encontraron reservas para el código de pista '" + codigoPista + "'.");
        }
    }
    public static void pistasPorCalle(Scanner scanner) {
        System.out.println("Introduce la calle para verificar cuántas pistas operativas hay:");
        scanner.nextLine();
        String calle = scanner.nextLine();
        int pistasOperativas = Operacions.contarPistasOperativasPorCalle(calle);
        System.out.println("El número de pistas operativas en los polideportivos de la calle '" + calle + "' es: " + pistasOperativas);
    }
    public static Integer eliminarPista() {
        System.out.println("Estás eliminando una pista\n");
        Scanner scannerEliminarPista = new Scanner(System.in);
        System.out.println("\nPistas existentes:");
        List<Pista> pistasListEliminar = Operacions.listarPistas();
        for (Pista p : pistasListEliminar) {
            System.out.println("ID: " + p.getIdPista() + " | Código: " + p.getCodigo() + " | Operativa: " + p.isOperativa());
        }
        System.out.print("Introduce el ID de la pista que deseas eliminar: ");
        int idPistaEliminar = scannerEliminarPista.nextInt();
        scannerEliminarPista.nextLine();
        Pista pistaSeleccionadaEliminar = Operacions.getDatosPista(idPistaEliminar);
        if (pistaSeleccionadaEliminar == null) {
            System.out.println("ID de pista no válido. Volviendo al menú principal...");
            return null;
        }
        return idPistaEliminar;
    }

    public static String solicitarCodigoPista() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el código de la pista: ");
        return scanner.nextLine();
    }
    public static boolean solicitarEstadoPista() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Está operativa? (true/false): ");
        return scanner.nextBoolean();
    }
    public static Polideportivos seleccionarOcrearPolideportivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n¿Quieres usar un polideportivo existente o crear uno nuevo?");
        System.out.println("1. Usar un polideportivo existente");
        System.out.println("2. Crear un nuevo polideportivo");

        int eleccion = scanner.nextInt();
        scanner.nextLine();

        if (eleccion == 1) {
            return seleccionarPolideportivoExistente();
        } else if (eleccion == 2) {
            return crearNuevoPolideportivo();
        } else {
            System.out.println("Opción no válida.");
            return null;
        }
    }

    private static Polideportivos seleccionarPolideportivoExistente() {
        List<Polideportivos> polideportivosList = Operacions.listarPolideportivos();
        System.out.println("\nPolideportivos existentes:");
        for (Polideportivos p : polideportivosList) {
            System.out.println(p);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del polideportivo: ");
        int idPolideportivo = scanner.nextInt();

        for (Polideportivos p : polideportivosList) {
            if (p.getIdPolideportivos() == idPolideportivo) {
                return p;
            }
        }
        System.out.println("ID no válido.");
        return null;
    }

    private static Polideportivos crearNuevoPolideportivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el nombre del nuevo polideportivo: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce la dirección del nuevo polideportivo: ");
        String direccion = scanner.nextLine();

        Polideportivos nuevoPolideportivo = new Polideportivos(0, nombre, direccion);
        if (Operacions.addPolideportivo(nuevoPolideportivo)) {
            System.out.println("Nuevo polideportivo creado correctamente.");
            List<Polideportivos> polideportivosList = Operacions.listarPolideportivos();
            for (Polideportivos p : polideportivosList) {
                if (p.getNombre().equals(nombre)) {
                    return p;
                }
            }
            return null;
        } else {
            System.out.println("Error al crear el polideportivo.");
            return null;
        }
    }

    public static Pista obtenerPistaPorId(Scanner scanner, List<Pista> pistasList) {
        System.out.print("Introduce el ID de la pista que deseas modificar: ");
        int idPista = scanner.nextInt();
        scanner.nextLine();
        for (Pista pista : pistasList) {
            if (pista.getIdPista() == idPista) {
                return pista;
            }
        }
        return null;
    }

    public static String solicitarNuevoCodigoPista(Scanner scanner, String codigoActual) {
        System.out.print("Introduce el nuevo código de la pista (actual: " + codigoActual + "): ");
        return scanner.nextLine();
    }

    public static boolean solicitarNuevoEstadoPista(Scanner scanner, boolean estadoActual) {
        System.out.print("¿Está operativa? (true/false, actual: " + estadoActual + "): ");
        return scanner.nextBoolean();
    }

    public static void mostrarPolideportivos(List<Polideportivos> polideportivosList) {
        for (Polideportivos p : polideportivosList) {
            System.out.println(p);
        }
    }

    public static void crearPolideportivovista(Scanner scannerModifPista, List<Polideportivos> polideportivosListModif, String nuevoCodigo, boolean nuevaOperativa, Pista pistaSeleccionada) {
        System.out.print("Introduce el ID del nuevo polideportivo asociado a la pista: ");
        int idPolideportivoNuevo = scannerModifPista.nextInt();
        scannerModifPista.nextLine();
        Polideportivos polideportivoSeleccionadomodif = null;
        for (Polideportivos p : polideportivosListModif) {
            if (p.getIdPolideportivos() == idPolideportivoNuevo) {
                polideportivoSeleccionadomodif = p;
                break;
            }
        }

        if (polideportivoSeleccionadomodif == null) {
            System.out.println("ID de polideportivo no válido. Volviendo al menú principal.");
            return;
        }
        Pista pistaModificada = new Pista(nuevoCodigo, nuevaOperativa, polideportivoSeleccionadomodif, pistaSeleccionada.getIdPista());
        boolean pistaActualizada = Operacions.updatePista(pistaModificada);
        if (pistaActualizada) {
            System.out.println("Pista modificada correctamente.");
        } else {
            System.out.println("Error al modificar la pista.");
        }
    }

    public static void mostrarListaPistas(List<Pista> pistasList) {
        for (Pista p : pistasList) {
            System.out.println("ID: " + p.getIdPista() + " | Código: " + p.getCodigo() + " | Operativa: " + p.isOperativa());
        }
    }

    public static void MensajesErrorCrearPista(Pista nuevaPista) {
        if (Operacions.addPista(nuevaPista)) {
            System.out.println("Pista añadida correctamente.");
        } else {
            System.out.println("Error al añadir la pista.");
        }
    }

    public static void messageListarReservas() {
        System.out.println("Listando todas las reservas:");
    }

    public static void messagePistasExistentes() {
        System.out.println("\nPistas existentes:");
    }

    public static void messageModifPista() {
        System.out.println("Estás modificando una pista\n");
    }

    public static void messageCrearPista() {
        System.out.println("Estas creando una nueva pista\n");
    }

    public static void errorIdPista() {
        System.out.println("ID de pista no válido. Volviendo al menú principal.");
    }

    public static void messageSalirMenu() {
        System.out.println("Saliendo del programa...");
    }

    public static void messagePistaAModif() {
        System.out.print("Introduce el ID de la pista que deseas modificar: ");
    }

    public static void messageErrorCrearPolid() {
        System.out.println("No se pudo seleccionar o crear un polideportivo. Volviendo al menú principal.");
    }

    public static void messageListarPolid() {
        System.out.println("\nPolideportivos existentes:");
    }

    public static void messageErrorSeleccionSwich() {
        System.out.println("Opción no válida. Inténtalo de nuevo.");
    }


}

package edu.badpals.controller;

import edu.badpals.baseDatos.Operacions;
import edu.badpals.baseDatos.Pista;
import edu.badpals.baseDatos.Polideportivos;
import edu.badpals.baseDatos.Reservas;
import edu.badpals.view.View;
import java.util.List;
import java.util.Scanner;

public class Controlador {

    public static void crearPista() {
        View.messageCrearPista();
        String codigo = View.solicitarCodigoPista();
        boolean operativa = View.solicitarEstadoPista();

        Polideportivos polideportivoSeleccionado = View.seleccionarOcrearPolideportivo();

        if (polideportivoSeleccionado == null) {
            View.messageErrorCrearPolid();
            return;
        }

        Pista nuevaPista = new Pista(codigo, operativa, polideportivoSeleccionado, 0);
        View.MensajesErrorCrearPista(nuevaPista);
    }

    public static void modificarPista(Scanner scanner) {
        View.messageModifPista();

        View.messagePistasExistentes();
        List<Pista> pistasList = Operacions.listarPistas();
        View.mostrarListaPistas(pistasList);

        View.messagePistaAModif();
        Pista pistaSeleccionada = View.obtenerPistaPorId(scanner, pistasList);

        if (pistaSeleccionada == null) {
            View.errorIdPista();
            return;
        }

        String nuevoCodigo = View.solicitarNuevoCodigoPista(scanner, pistaSeleccionada.getCodigo());
        boolean nuevaOperativa = View.solicitarNuevoEstadoPista(scanner, pistaSeleccionada.isOperativa());

        View.messageListarPolid();
        List<Polideportivos> polideportivosListModif = Operacions.listarPolideportivos();
        View.mostrarPolideportivos(polideportivosListModif);

        View.crearPolideportivovista(scanner, polideportivosListModif, nuevoCodigo, nuevaOperativa, pistaSeleccionada);
    }

    public static void eliminarPista() {
        Integer idPistaEliminar = View.eliminarPista();
        if (idPistaEliminar == null) return;
        Operacions.deletePista(idPistaEliminar);
    }

    public static void listarReservas() {
        View.messageListarReservas();
        List<Reservas> reservasList = Operacions.listadoReservas();
        View.mostrarListasReservas(reservasList);
    }

    public static void mostrarPistaMasRentable() {
        Operacions.pistaConMasDinero();
    }

    public static void listarReservasSinIva() {
        View.messageListarReservas();
        List<Reservas> reservasListSiniva = Operacions.listarReservasSinIva();
        View.mostrarListasReservas(reservasListSiniva);
    }

    public static void mostrarMetadatosBBDD(Scanner scanner) {
        String nombreTabla = View.nombreMetadatos("Introduce el nombre de la tabla:", scanner);
        View.ensenarInforBBDD(Operacions.getInformacionConexion("polideportivos", nombreTabla));
    }
}


package edu.badpals.model;

public class Reservas {
    private int idReservas;
    private double precio;
    private Pistas pistas;

    public Reservas(int idReservas, double precio, int pistas) {
        this.idReservas = idReservas;
        this.precio = precio;
        this.pistas = pistas;
    }

    public int getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(int idReservas) {
        this.idReservas = idReservas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getPistas() {
        return pistas;
    }

    public void setPistas(int pistas) {
        this.pistas = pistas;
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "idReservas=" + idReservas +
                ", precio=" + precio +
                ", pistas=" + pistas +
                '}';
    }
}

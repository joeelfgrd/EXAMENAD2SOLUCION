package edu.badpals.baseDatos;

public class Reservas {
    private int idReservas;
    private double precio;
    private Pista pista;

    public Reservas(int idReservas, double precio, Pista pista) {
        this.idReservas = idReservas;
        this.precio = precio;
        this.pista = pista;
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

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "idReservas=" + idReservas +
                ", precio=" + precio +
                ", pista=" + pista +
                '}';
    }
}

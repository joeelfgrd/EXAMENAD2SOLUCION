package edu.badpals.model;

public class Polideportivos {
    private int idPolideportivos;
    private String nombre;
    private String direccion;

    public Polideportivos(int idPolideportivos, String nombre, String direccion) {
        this.idPolideportivos = idPolideportivos;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getIdPolideportivos() {
        return idPolideportivos;
    }

    public void setIdPolideportivos(int idPolideportivos) {
        this.idPolideportivos = idPolideportivos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Polideportivos{" +
                "idPolideportivos=" + idPolideportivos +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

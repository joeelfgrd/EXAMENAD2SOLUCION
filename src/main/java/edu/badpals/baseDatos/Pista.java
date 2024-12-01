package edu.badpals.baseDatos;

public class Pista {

    private int idPista;
    private String codigo;
    private boolean operativa;
    private Polideportivos polideportivos;

    public Pista(String codigo, boolean operativa, Polideportivos polideportivos, int idPista) {
        this.codigo = codigo;
        this.operativa = operativa;
        this.polideportivos = polideportivos;
        this.idPista = idPista;
    }

    public int getIdPista() {
        return idPista;
    }

    public void setIdPista(int idPista) {
        this.idPista = idPista;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isOperativa() {
        return operativa;
    }

    public void setOperativa(boolean operativa) {
        this.operativa = operativa;
    }

    public Polideportivos getPolideportivos() {
        return polideportivos;
    }

    public void setPolideportivos(Polideportivos polideportivos) {
        this.polideportivos = polideportivos;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "idPista=" + idPista +
                ", codigo='" + codigo + '\'' +
                ", operativa=" + operativa +
                ", polideportivos=" + polideportivos +
                '}';
    }
}

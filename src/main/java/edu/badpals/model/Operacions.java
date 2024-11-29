package edu.badpals.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operacions {

    private static Connection conexion = null;

    private static void crearConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urldb = "jdbc:mysql://localhost:3306/Polideportivos";
            conexion = DriverManager.getConnection(urldb, "root", "root");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addPista(Pista pista) {
        try {
            String newClisql = "INSERT INTO pista (idPista, codigo, operativa, polideportivos) VALUES (?, ?, ?, ?)";
            crearConexion();
            PreparedStatement psnewPista = conexion.prepareStatement(newClisql);
            psnewPista.setInt(1, pista.getIdPista());
            psnewPista.setString(2, pista.getCodigo());
            psnewPista.setBoolean(3, pista.isOperativa());
            psnewPista.setObject(4, pista.getPolideportivos());
            int rowsAffected = psnewPista.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAffected);
            psnewPista.close();
            cerrarConexion();
            return rowsAffected;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int deletePista(Integer idCliente) {
        try {
            String delPistasql = "DELETE FROM pista WHERE idPista = ?";
            crearConexion();
            PreparedStatement psDel = conexion.prepareStatement(delPistasql);
            psDel.setInt(1, idCliente);
            int rowsAffected = psDel.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAffected);
            psDel.close();
            cerrarConexion();
            return rowsAffected;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int updatePista(Pista pista) {
        try {
            String modifPissql = "UPDATE pista SET idPista = ?, codigo = ?, operativa = ? , polideportivos = ? WHERE idPista = ?";
            crearConexion();
            PreparedStatement psmodif = conexion.prepareStatement(modifPissql);
            psmodif.setInt(1, pista.getIdPista());
            psmodif.setString(2, pista.getCodigo());
            int rsOperativa = calcularOperativa(pista.isOperativa());
            psmodif.setInt(3, rsOperativa);
            psmodif.setInt(4, pista.getPolideportivos());
            psmodif.setInt(5, pista.getIdPista());
            int rowsAfected = psmodif.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAfected);
            psmodif.close();
            cerrarConexion();
            return rowsAfected;
        } catch (SQLException e) {
            return 0;
        }
    }
    public static int calcularOperativa(boolean operativa){
        int operativanum=0;
        if (operativa){
            operativanum=1;
            return operativanum;
        }else{
            return operativanum;
        }
    }
    public static ArrayList<String> getPistaMaxGenerado() {
        ArrayList<String> datos = new ArrayList<>();
        try {
            String PistaMaxsql = "SELECT idReservas, COUNT(idReservas) AS numReservas, SUM(precio) AS sumaTotal FROM reservas GROUP BY id_Pista ORDER BY sumaTotal DESC LIMIT 1;";
            crearConexion();
            PreparedStatement psPisMaxgen = conexion.prepareStatement(PistaMaxsql);
            ResultSet rs = psPisMaxgen.executeQuery();
            if (rs.next()) {
                int idPista = rs.getInt("id_Pista");
                int numReservas = rs.getInt("numReservas");
                int sumaTotal = rs.getInt("sumaTotal");
                datos.add(String.valueOf(idPista));
                datos.add(String.valueOf(numReservas));
                datos.add(String.valueOf(sumaTotal));
            }
            rs.close();
            psPisMaxgen.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en Operacions getPistaMaxGenerado");
        }
        return datos;
    }
    public static List<Double> getListadoPreciosReservas(String CodigoPista) {
        List<Double> precios = new ArrayList<>();
        try {
            String procListFac = "{CALL pr_CodigoPrezo(?)}";
            crearConexion();
            CallableStatement cs = conexion.prepareCall(procListFac);
            cs.setString(1, CodigoPista);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                precios.add(rs.getDouble("precio"));
            }
            rs.close();
            cs.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(precios);
        return precios;
    }
    private static Pista getDatosPista(int idPista) throws SQLException {
        String datosPistasql = "SELECT * FROM pista WHERE idPista = ?";
        crearConexion();
        PreparedStatement psPis = conexion.prepareStatement(datosPistasql);
        psPis.setInt(1, idPista);
        ResultSet rs = psPis.executeQuery();
        Pista pista = null;
        if (rs.next()) {
            int id = rs.getInt("idPista");
            String codigo = rs.getString("codigo");
            boolean operativa = rs.getBoolean("operativa");
            int id_polideportivo = rs.getInt("id_polideportivo");
            pista = new Pista(id,codigo,operativa,id_polideportivo);
        }
        rs.close();
        psPis.close();
        cerrarConexion();
        return pista;

    }
    public static ArrayList<Reservas> getListadoReservasSinIva() {
        ArrayList<Reservas> facturas = new ArrayList<>();
        try {
            String ResSinIvasql = "SELECT * FROM reservas";
            crearConexion();
            PreparedStatement psresiva = conexion.prepareStatement(ResSinIvasql);
            ResultSet rs = psresiva.executeQuery();
            while (rs.next()) {
                int idReservas = rs.getInt("idReservas");
                double precio = rs.getDouble("precio");
                double precioSinIVA = (precio - (precio * 21) / 100);
                int idPista = rs.getInt("idPista");
                facturas.add(new Reservas(idReservas, precioSinIVA, idPista));
            }
            rs.close();
            psresiva.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en getListadoFacturasSinIva en Operacions");
        }
        return facturas;
    }




    public static ResultSet getInformacionConexion(String esquema, String tabla) {
        try {
            crearConexion();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getColumns(esquema, null, tabla, null);
            cerrarConexion();
            return columns;
        } catch (SQLException e) {
            System.out.println("Error en getInformacionConexion en Conexion");
            return null;
        }
    }


}

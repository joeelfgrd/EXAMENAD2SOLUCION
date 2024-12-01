package edu.badpals.baseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operacions {

    private static Connection conexion = null;

    private static void abrirConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urldb = "jdbc:mysql://localhost:3306/polideportivos";
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
    //----------------PRIMERA PARTE CRUD----------------

    //Crear nueva Pista
    public static boolean addPista(Pista pista) {
        boolean resultado = false;
        String newPistasql = "INSERT INTO pista (codigo, operativa, id_polideportivo) VALUES (?, ?, ?)";
        abrirConexion();
        try (PreparedStatement psttnewPista = conexion.prepareStatement(newPistasql)) {
            psttnewPista.setString(1, pista.getCodigo());
            psttnewPista.setBoolean(2, pista.isOperativa());
            psttnewPista.setInt(3, pista.getPolideportivos().getIdPolideportivos());
            int filasAfectadas = psttnewPista.executeUpdate();
            resultado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return resultado;
    }

    //Modificar Pista
    public static boolean updatePista(Pista pista) {
        boolean resultado = false;
        String modifPistasql = "UPDATE pista SET codigo = ?, operativa = ?, id_polideportivo = ? WHERE idPista = ?";
        abrirConexion();
        try (PreparedStatement psttmodifPista = conexion.prepareStatement(modifPistasql)) {
            psttmodifPista.setString(1, pista.getCodigo());
            psttmodifPista.setBoolean(2, pista.isOperativa());
            psttmodifPista.setInt(3, pista.getPolideportivos().getIdPolideportivos());
            psttmodifPista.setInt(4, pista.getIdPista());
            int filasAfectadas = psttmodifPista.executeUpdate();
            resultado = filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return resultado;
    }

    //Eliminar Pista
    //Se podría tener en cuenta que para eliminar las pistas no podria haber reservas asociadas,pero como no es materia del examen no lo traté
    public static int deletePista(int idPista) {
        String delPistasql = "DELETE FROM pista WHERE idPista = ?";
        abrirConexion();
        try (PreparedStatement psDel = conexion.prepareStatement(delPistasql)) {
            psDel.setInt(1, idPista);
            int rowsAffected = psDel.executeUpdate();
            System.out.println("Se ha eliminado la pista con id: " + idPista);
            System.out.println("Se han eliminado " + rowsAffected + " filas.");
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            cerrarConexion();
        }
    }

    //Listar Reservas
    //Hice los dos porque en el enunciado no dejaba claro que datos tenia que aparecer,pero si yo tuviera que como usuario elegir uno,sería el segundo
    //Importante recordar que si cambio o descomento aqui tambien tengo que cambiar en View MostrarListasReservas


    public static List<Reservas> listadoReservas() {
        List<Reservas> listaReservas = new ArrayList<>();
        String listaReservassql = """
        SELECT r.idReservas, r.precio,p.idPista, p.codigo, p.operativa,pol.idPolideportivos, pol.nombre AS nombrePolideportivo, pol.direccion
            FROM reservas r
                JOIN pista p ON r.id_pista = p.idPista
                    JOIN polideportivos pol ON p.id_polideportivo = pol.idPolideportivos
        """;

        abrirConexion();
        try (PreparedStatement psttlistaReservas = conexion.prepareStatement(listaReservassql);
             ResultSet resultSet = psttlistaReservas.executeQuery()) {

            while (resultSet.next()) {
                int idReserva = resultSet.getInt("idReservas");
                double precio = resultSet.getDouble("precio");
                int idPista = resultSet.getInt("idPista");
                String codigoPista = resultSet.getString("codigo");
                boolean operativa = resultSet.getBoolean("operativa");
                int idPolideportivo = resultSet.getInt("idPolideportivos");
                String nombrePolideportivo = resultSet.getString("nombrePolideportivo");
                String direccionPolideportivo = resultSet.getString("direccion");


                Polideportivos polideportivo = new Polideportivos(idPolideportivo, nombrePolideportivo, direccionPolideportivo);
                Pista pista = new Pista(codigoPista, operativa, polideportivo, idPista);
                Reservas reserva = new Reservas(idReserva, precio, pista);


                listaReservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaReservas;
    }
    /*public static List<Reservas> listadoReservas() {
        List<Reservas> listaReservas = new ArrayList<>();
        String listaReservassql = """
            SELECT r.idReservas, r.precio, p.codigo, p.id_polideportivo, pol.nombre AS nombrePolideportivo 
                FROM reservas r 
                    JOIN pista p ON r.id_pista = p.idPista 
                        JOIN polideportivos pol ON p.id_polideportivo = pol.idPolideportivos
        """;
        abrirConexion();
        try (PreparedStatement psttlistaReservas = conexion.prepareStatement(listaReservassql);
             ResultSet resultSet = psttlistaReservas.executeQuery()) {
            while (resultSet.next()) {
                int idReserva = resultSet.getInt("idReservas");
                double precio = resultSet.getDouble("precio");
                String codigoPista = resultSet.getString("codigo");
                String nombrePolideportivo = resultSet.getString("nombrePolideportivo");
                Pista pista = new Pista(codigoPista, true, new Polideportivos(resultSet.getInt("id_polideportivo"), nombrePolideportivo, ""), 0);
                listaReservas.add(new Reservas(idReserva, precio, pista));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaReservas;
    }*/


    //SEGUNDA PARTE

    //Pista que mas ingresos ha generado
    public static Pista pistaConMasDinero() {
        Pista pistaConMasDinero = null;
        String sql = "SELECT r.id_pista, SUM(r.precio) AS total_generado FROM reservas r GROUP BY r.id_pista ORDER BY total_generado DESC LIMIT 1";
        abrirConexion();
        try (PreparedStatement pstt = conexion.prepareStatement(sql);
             ResultSet resultSet = pstt.executeQuery()) {

            if (resultSet.next()) {
                int idPista = resultSet.getInt("id_pista");
                double totalGenerado = resultSet.getDouble("total_generado");
                pistaConMasDinero = getDatosPista(idPista);
                System.out.println("La pista que más dinero ha generado es:");
                System.out.println("Pista ID: " + pistaConMasDinero.getIdPista());
                System.out.println("Código: " + pistaConMasDinero.getCodigo());
                System.out.println("Total generado: " + totalGenerado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return pistaConMasDinero;
    }

    //Mostar reservas sin IVA
    public static List<Reservas> listarReservasSinIva() {
        List<Reservas> listaReservas = new ArrayList<>();
        String listaReservassql = "SELECT idReservas, precio, id_pista FROM reservas";
        abrirConexion();
        try (PreparedStatement psttlistaReservas = conexion.prepareStatement(listaReservassql);
             ResultSet resultSet = psttlistaReservas.executeQuery()) {
            while (resultSet.next()) {
                int idReservas = resultSet.getInt("idReservas");
                double precio = resultSet.getDouble("precio");
                double precioSinIVA = precio - ((precio * 21) / 100);
                int idPista = resultSet.getInt("id_pista");
                Pista pista = getDatosPista(idPista);
                listaReservas.add(new Reservas(idReservas, precioSinIVA, pista));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaReservas;
    }

    //Pistas por calle
    public static int contarPistasOperativasPorCalle(String calle) {
        int pistasOperativas = 0;
        String sql = "SELECT COUNT(*) AS numPistasOperativas FROM pista WHERE id_polideportivo IN (SELECT idPolideportivos FROM polideportivos WHERE direccion = ?) AND operativa = TRUE;";
        try {
            abrirConexion();
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, calle);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pistasOperativas = rs.getInt("numPistasOperativas");
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error en contarPistasOperativasPorCalle en Conexion");
            e.printStackTrace();
        }finally {
            cerrarConexion();
        }
        return pistasOperativas;
    }
    //Procemiento almacenado para obtener el precio de las reservas
    public static List<Double> getListadoPreciosReservas(String CodigoPista) {
        List<Double> precios = new ArrayList<>();
        try {
            String procListFac = "{CALL pr_CodigoPrezo(?)}";
            abrirConexion();
            CallableStatement cs = conexion.prepareCall(procListFac);
            cs.setString(1, CodigoPista);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                precios.add(rs.getDouble("precio"));
            }
            rs.close();
            cs.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }finally {
            cerrarConexion();
        }
        System.out.println(precios);
        return precios;
    }
    //TERCERA PARTE
    //Metadatos obtener informacion de las tablas
    public static ResultSet getInformacionConexion(String esquema, String tabla) {
        try {
            abrirConexion();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getColumns(esquema, null, tabla, null);

            return columns;
        } catch (SQLException e) {
            System.out.println("Error en getInformacionConexion en Conexion");
            return null;
        }finally {
            cerrarConexion();
        }

    }


    //He creado estes metodo para hacer más realista la interaccion con el usuario, ya
    // que tal vez quiera al añadir una pista usar un polideportivo que ya exista,etc

    public static List<Polideportivos> listarPolideportivos() {
        List<Polideportivos> listaPolideportivos = new ArrayList<>();
        String listaPolidsql = "SELECT idPolideportivos, nombre, direccion FROM polideportivos";
        abrirConexion();
        try (PreparedStatement psttlistaPoli = conexion.prepareStatement(listaPolidsql);
             ResultSet resultSet = psttlistaPoli.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idPolideportivos");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                listaPolideportivos.add(new Polideportivos(id, nombre, direccion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaPolideportivos;
    }
    //También creo este metodo para manejar también la interaccion del usuario al crear las Pistas
    // asi puede elegir si usar un polideportivo ya existente o crear uno nuevo

    public static boolean addPolideportivo(Polideportivos polideportivos) {
        boolean resultado = false;
        String newPolideportivosql = "INSERT INTO polideportivos (nombre, direccion) VALUES (?, ?)";
        abrirConexion();
        try (PreparedStatement psttnewPoli = conexion.prepareStatement(newPolideportivosql)) {
            psttnewPoli.setString(1, polideportivos.getNombre());
            psttnewPoli.setString(2, polideportivos.getDireccion());
            int filasAfectadas = psttnewPoli.executeUpdate();
            resultado = filasAfectadas > 0;
            if (resultado) {
                System.out.println("Polideportivo añadido correctamente.");
            } else {
                System.out.println("No se ha podido añadir el polideportivo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return resultado;
    }


    //Metodos auxiliares
    private static Polideportivos getDatosPolideportivo(int idPolideportivo) {
        String datosPolideportivosql = "SELECT nombre, direccion FROM polideportivos WHERE idPolideportivos = ?";
        Polideportivos polideportivos = null;
        abrirConexion();
        try (PreparedStatement psttDatosPoli = conexion.prepareStatement(datosPolideportivosql)) {
            psttDatosPoli.setInt(1, idPolideportivo);
            try (ResultSet resultSet = psttDatosPoli.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String direccion = resultSet.getString("direccion");
                    polideportivos = new Polideportivos(idPolideportivo, nombre, direccion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return polideportivos;
    }


    public static List<Pista> listarPistas() {
        List<Pista> listaPistas = new ArrayList<>();
        String listaPistasql = "SELECT idPista, codigo, operativa, id_polideportivo FROM pista";
        abrirConexion();
        try (PreparedStatement psttlistaPistas = conexion.prepareStatement(listaPistasql);
             ResultSet resultSet = psttlistaPistas.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idPista");
                String codigo = resultSet.getString("codigo");
                boolean operativa = resultSet.getBoolean("operativa");
                int id_polideportivo = resultSet.getInt("id_polideportivo");
                Polideportivos polideportivos = getDatosPolideportivo(id_polideportivo);
                listaPistas.add(new Pista(codigo, operativa, polideportivos, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return listaPistas;
    }

    public static Pista getDatosPista(int idPistaEliminar) {
        String datosPistasql = "SELECT codigo, operativa, id_polideportivo FROM pista WHERE idPista = ?";
        Pista pista = null;
        abrirConexion();
        try (PreparedStatement psttDatosPista = conexion.prepareStatement(datosPistasql)) {
            psttDatosPista.setInt(1, idPistaEliminar);
            try (ResultSet resultSet = psttDatosPista.executeQuery()) {
                if (resultSet.next()) {
                    String codigo = resultSet.getString("codigo");
                    boolean operativa = resultSet.getBoolean("operativa");
                    int id_polideportivo = resultSet.getInt("id_polideportivo");
                    Polideportivos polideportivos = getDatosPolideportivo(id_polideportivo);
                    pista = new Pista(codigo, operativa, polideportivos, idPistaEliminar);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return pista;
    }
}





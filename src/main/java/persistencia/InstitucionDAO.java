/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;
import java.sql.*;
import java.util.ArrayList;
import logica.Institucion;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class InstitucionDAO {
    Conexion conexion = new Conexion();
    public Institucion obtenerInstitucionporID(int idInstitucion){
        Institucion institucion=null;
        String sql="SELECT * FROM institucion WHERE id_institucion = ?";
        try (Connection conn = conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idInstitucion);
        try(ResultSet rs = stmt.executeQuery()){
            if (rs.next()) {
                institucion = new Institucion();
                institucion.setId_institucion(rs.getInt("id_institucion"));
                institucion.setNombre(rs.getString("nombre"));
                institucion.setLocalidad(rs.getInt("id_localidad"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return institucion;
}
    public ArrayList<Institucion> obtenerInstitucionesPorLocalidad(int id_localidad) {
            ArrayList<Institucion> lista = new ArrayList<>();
        String sql = "* FROM institucion WHERE id_localidad=? AND tipo='NEP' ORDER BY nombre ASC";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Institucion l = new Institucion();
                l.setId_institucion(rs.getInt("id_institucion"));
                l.setNombre(rs.getString("nombre"));
                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Institucion> listadoInstituciones() {
            ArrayList<Institucion> lista = new ArrayList<>();
        String sql = "SELECT id_institucion, nombre FROM institucion ORDER BY nombre ASC";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Institucion l = new Institucion();
                l.setId_institucion(rs.getInt("id_institucion"));
                l.setNombre(rs.getString("nombre"));
                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
}

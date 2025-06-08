/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;
import java.sql.*;
import java.util.ArrayList;
import logica.Localidad;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class LocalidadDAO {
 public ArrayList<Localidad> obtenerTodas() {
        ArrayList<Localidad> lista = new ArrayList<>();
        String sql = "SELECT id_localidad, nombre FROM localidad ORDER BY nombre ASC";
        Conexion conexion = new Conexion();
    
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Localidad l = new Localidad();
                l.setId_localidad(rs.getInt("id_localidad"));
                l.setNombre(rs.getString("nombre"));
                lista.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

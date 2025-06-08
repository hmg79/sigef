/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.util.ArrayList;
import java.util.List;
import logica.EstadisticaCurso;
import java.sql.*;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class InformesDAO {
    Conexion conexion = new Conexion();
    public List<EstadisticaCurso> obtenerEstadisticaPorTipoCurso(int idInstitucion){
        List<EstadisticaCurso> lista=new ArrayList<>();
        String SQL="SELECT n.nombre AS tipocapacitacion," +
                "COUNT(*) AS total," +
                "SUM(CASE WHEN i.estado = 'inscripto' THEN 1 ELSE 0 END) AS inscripto," +
                "SUM(CASE WHEN i.estado = 'egresado' THEN 1 ELSE 0 END) AS egresado," +
                "SUM(CASE WHEN i.estado = 'cursando' THEN 1 ELSE 0 END) AS cursando " +
                "FROM `inscripción` i" +
                "JOIN tipocapacitacion t ON i.id_curso = t.id_tipocapacitacion"+
                "JOIN nivel n ON t.id_nivel = n.id_nivel"+
                "WHERE i.id_institucion = ?"+
                "GROUP BY n.nombre";
        try(Connection conn=conexion.getConnection();
            PreparedStatement stmt=conn.prepareStatement(SQL)){
               stmt.setInt(1, idInstitucion);
               ResultSet rs=stmt.executeQuery();
               System.out.println("Realizando la consulta a la base de datos para la institucion" + idInstitucion);
               while(rs.next()){
                   EstadisticaCurso estadistica=new EstadisticaCurso();
                   estadistica.setTipo(rs.getString("tipocapacitacion"));
                   estadistica.setTotal(rs.getInt("total"));
                   estadistica.setCursando(rs.getInt("cursando"));
                   estadistica.setEgresado(rs.getInt("egresado"));
                   estadistica.setInscripto(rs.getInt("inscripto"));
                   lista.add(estadistica);
                 //System.out.println("Fila Obtenida: " + rs.getString("tipocapacitacion"));
               }
            } catch (SQLException e){
                e.printStackTrace();
            }
        return lista;
    }
   public List<EstadisticaCurso> obtenerEstadisticasPorCurso(int idInstitucion) {
    List<EstadisticaCurso> lista = new ArrayList<>();

    String sql = "SELECT tc.nombre AS curso, " +
                 "COUNT(*) AS total, " +
                 "SUM(CASE WHEN i.estado = 'inscripto' THEN 1 ELSE 0 END) AS inscripto, " +
                 "SUM(CASE WHEN i.estado = 'egresado' THEN 1 ELSE 0 END) AS egresado, " +
                 "SUM(CASE WHEN i.estado = 'cursando' THEN 1 ELSE 0 END) AS cursando " +
                 "FROM inscripción i " +
                 "JOIN tipocapacitacion tc ON i.id_tipocapacitacion = tc.id_tipocapacitacion " +
                 "WHERE i.id_institucion = ? " +
                 "GROUP BY tc.nombre " +
                 "ORDER BY tc.nombre ASC";

    try (Connection conn = conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idInstitucion);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EstadisticaCurso ec = new EstadisticaCurso();
                ec.setNombreCurso(rs.getString("curso"));
                ec.setTotal(rs.getInt("total"));
                ec.setInscripto(rs.getInt("inscripto"));
                ec.setEgresado(rs.getInt("egresado"));
                ec.setCursando(rs.getInt("cursando"));
                lista.add(ec);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
    
}
}

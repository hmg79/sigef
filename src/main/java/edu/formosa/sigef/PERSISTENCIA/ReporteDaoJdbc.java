/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Logica de comunicación con la base de datos
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.dto.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDaoJdbc implements ReporteDao {
  @Override
public List<AlumnosPorLocalidad> cantidadAlumnosPorLocalidad(Long periodoId, Long institucionId, Long localidadId, Integer estadoId) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT l.id AS localidad_id, l.nombre AS localidad_nombre, COUNT(DISTINCT a.id) AS total ")
      .append("FROM matricula m ")
      .append("JOIN alumno a ON a.id = m.alumno_id ")
      .append("JOIN institucion_curso ic ON ic.id = m.institucion_curso_id ")
      .append("JOIN institucion i ON i.id = ic.institucion_id ")
      .append("JOIN localidad l ON l.id = i.localidad_id ")
      .append("WHERE 1=1 ");
    if (periodoId != null)    sb.append("AND m.periodo_id = ? ");
    if (institucionId != null)sb.append("AND i.id = ? ");
    if (localidadId != null)  sb.append("AND l.id = ? ");
    if (estadoId != null)     sb.append("AND m.estado_id = ? ");
    sb.append("GROUP BY l.id, l.nombre ORDER BY l.nombre");

    try (Connection cn = DB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sb.toString())) {
        int idx=1;
        if (periodoId != null)     ps.setLong(idx++, periodoId);
        if (institucionId != null) ps.setLong(idx++, institucionId);
        if (localidadId != null)   ps.setLong(idx++, localidadId);
        if (estadoId != null)      ps.setInt(idx++, estadoId);

        List<AlumnosPorLocalidad> out = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new AlumnosPorLocalidad(
                        rs.getLong("localidad_id"),
                        rs.getString("localidad_nombre"),
                        rs.getLong("total")
                ));
            }
        }
        return out;
    } catch (SQLException e) {
        throw new PersistenciaExcepciones("Error reporte por localidad", e);
    }
}
     @Override
    public List<AlumnosPorNivel> cantidadAlumnosPorNivel(Long periodoId, Long institucionId, Integer estadoId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT n.id AS nivel_id, n.nombre AS nivel_nombre, COUNT(DISTINCT a.id) AS total ")
          .append("FROM matricula m ")
          .append("JOIN alumno a ON a.id = m.alumno_id ")
          .append("JOIN institucion_curso ic ON ic.id = m.institucion_curso_id ")
          .append("JOIN curso_catalogo cc ON cc.id = ic.curso_catalogo_id ")
          .append("JOIN nivel n ON n.id = cc.nivel_id ")
          .append("JOIN institucion i ON i.id = ic.institucion_id ")
          .append("WHERE 1=1 ");
        if (periodoId != null) sb.append("AND m.periodo_id = ? ");
        if (institucionId != null) sb.append("AND i.id = ? ");
        if (estadoId != null) sb.append("AND m.estado_id = ? ");
        sb.append("GROUP BY n.id, n.nombre ORDER BY n.nombre");

        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sb.toString())) {
            int idx=1;
            if (periodoId != null) ps.setLong(idx++, periodoId);
            if (institucionId != null) ps.setLong(idx++, institucionId);
            if (estadoId != null) ps.setInt(idx++, estadoId);
            List<AlumnosPorNivel> out = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new AlumnosPorNivel(
                            rs.getLong("nivel_id"),
                            rs.getString("nivel_nombre"),
                            rs.getLong("total")
                    ));
                }
            }
            return out;
        } catch (SQLException e) { throw new PersistenciaExcepciones("Error reporte por nivel", e); }
    }

    @Override
    public List<AlumnosPorCurso> cantidadAlumnosPorCurso(Long periodoId, Long institucionId, Integer estadoId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cc.id AS curso_id, cc.nombre AS curso_nombre, COUNT(DISTINCT a.id) AS total ")
          .append("FROM matricula m ")
          .append("JOIN alumno a ON a.id = m.alumno_id ")
          .append("JOIN institucion_curso ic ON ic.id = m.institucion_curso_id ")
          .append("JOIN curso_catalogo cc ON cc.id = ic.curso_catalogo_id ")
          .append("JOIN institucion i ON i.id = ic.institucion_id ")
          .append("WHERE 1=1 ");
        if (periodoId != null) sb.append("AND m.periodo_id = ? ");
        if (institucionId != null) sb.append("AND i.id = ? ");
        if (estadoId != null) sb.append("AND m.estado_id = ? ");
        sb.append("GROUP BY cc.id, cc.nombre ORDER BY cc.nombre");

        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sb.toString())) {
            int idx=1;
            if (periodoId != null) ps.setLong(idx++, periodoId);
            if (institucionId != null) ps.setLong(idx++, institucionId);
            if (estadoId != null) ps.setInt(idx++, estadoId);
            List<AlumnosPorCurso> out = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new AlumnosPorCurso(
                            rs.getLong("curso_id"),
                            rs.getString("curso_nombre"),
                            rs.getLong("total")
                    ));
                }
            }
            return out;
        } catch (SQLException e) { throw new PersistenciaExcepciones("Error reporte por curso", e); }
    }

    @Override
    public List<AlumnosPorCondicion> cantidadPorCondicion(Long periodoId, Long institucionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m.estado_id AS estado_id, ")
          .append("CASE m.estado_id ")
          .append(" WHEN 1 THEN 'Preinscripto' ")
          .append(" WHEN 2 THEN 'Cursando' ")
          .append(" WHEN 3 THEN 'Abandono' ")
          .append(" WHEN 4 THEN 'Egresado' ")
          .append(" ELSE 'Otro' END AS estado, ")
          .append("COUNT(DISTINCT a.id) AS total ")
          .append("FROM matricula m ")
          .append("JOIN alumno a ON a.id = m.alumno_id ")
          .append("JOIN institucion_curso ic ON ic.id = m.institucion_curso_id ")
          .append("JOIN institucion i ON i.id = ic.institucion_id ")
          .append("WHERE 1=1 ");
        if (periodoId != null) sb.append("AND m.periodo_id = ? ");
        if (institucionId != null) sb.append("AND i.id = ? ");
        sb.append("GROUP BY m.estado_id ORDER BY m.estado_id");

        try (Connection cn = DB.getConnection(); PreparedStatement ps = cn.prepareStatement(sb.toString())) {
            int idx=1;
            if (periodoId != null) ps.setLong(idx++, periodoId);
            if (institucionId != null) ps.setLong(idx++, institucionId);
            List<AlumnosPorCondicion> out = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new AlumnosPorCondicion(
                            rs.getInt("estado_id"),
                            rs.getString("estado"),
                            rs.getLong("total")
                    ));
                }
            }
            return out;
        } catch (SQLException e) { throw new PersistenciaExcepciones("Error reporte por condición", e); }
    }
}

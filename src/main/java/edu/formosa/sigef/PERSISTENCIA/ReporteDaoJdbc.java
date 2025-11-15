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
public List<AlumnosPorLocalidad> cantidadAlumnosPorLocalidad(
        Long periodoId, Long institucionId, Long localidadId, Integer estadoId) {

    List<AlumnosPorLocalidad> lista = new ArrayList<>();

    String sql = """
        SELECT 
            l.id   AS localidad_id,
            l.nombre AS localidad_nombre,
            SUM(CASE WHEN e.codigo = 'INSCRIPTO' THEN 1 ELSE 0 END) AS inscriptos,
            SUM(CASE WHEN e.codigo = 'CURSANDO'  THEN 1 ELSE 0 END) AS cursando,
            SUM(CASE WHEN e.codigo = 'EGRESADO'  THEN 1 ELSE 0 END) AS egresados,
            SUM(CASE WHEN e.codigo = 'ABANDONO'  THEN 1 ELSE 0 END) AS abandono,
            COUNT(*) AS total
        FROM matricula m
        JOIN estado_matricula e     ON e.id = m.estado_id
        JOIN institucion_curso ic   ON ic.id = m.institucion_curso_id
        JOIN institucion i          ON i.id = ic.institucion_id
        JOIN localidad l            ON l.id = i.localidad_id
        WHERE (i.id = ? OR ? IS NULL)
          AND (l.id = ? OR ? IS NULL)
          AND (m.periodo_id = ? OR ? IS NULL)
        GROUP BY l.id, l.nombre
        ORDER BY l.nombre
        """;

    try (Connection cn = DB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setObject(1, institucionId);
        ps.setObject(2, institucionId);
        ps.setObject(3, localidadId);
        ps.setObject(4, localidadId);
        ps.setObject(5, periodoId);
        ps.setObject(6, periodoId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new AlumnosPorLocalidad(
                        rs.getLong("localidad_id"),
                        rs.getString("localidad_nombre"),
                        rs.getInt("inscriptos"),
                        rs.getInt("cursando"),
                        rs.getInt("egresados"),
                        rs.getInt("abandono"),
                        rs.getInt("total")
                ));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw new PersistenciaExcepciones("Error al generar reporte por localidad", e);
    }

    return lista;
}


     @Override
    public List<AlumnosPorNivel> cantidadAlumnosPorNivel(Long periodoId, Long institucionId, Integer estadoId) {
         String sql = """
            SELECT
                n.id   AS nivel_id,
                n.nombre AS nivel_nombre,
                SUM(CASE WHEN e.codigo = 'INSCRIPTO' THEN 1 ELSE 0 END) AS inscriptos,
                SUM(CASE WHEN e.codigo = 'CURSANDO'  THEN 1 ELSE 0 END) AS cursando,
                SUM(CASE WHEN e.codigo = 'EGRESADO'  THEN 1 ELSE 0 END) AS egresados,
                SUM(CASE WHEN e.codigo = 'ABANDONO'  THEN 1 ELSE 0 END) AS abandono,
                COUNT(*) AS total
            FROM matricula m
            JOIN estado_matricula   e  ON e.id = m.estado_id
            JOIN institucion_curso  ic ON ic.id = m.institucion_curso_id
            JOIN curso_catalogo     cc ON cc.id = ic.curso_catalogo_id
            JOIN nivel              n  ON n.id = cc.nivel_id
            JOIN institucion        i  ON i.id = ic.institucion_id
            WHERE
                (? IS NULL OR m.periodo_id = ?)
                AND (? IS NULL OR i.id = ?)
                AND (? IS NULL OR e.id = ?)
            GROUP BY n.id, n.nombre
            ORDER BY n.nombre
            """;

         List<AlumnosPorNivel> out = new java.util.ArrayList<>();
        try (var cn = DB.getConnection();
            var ps = cn.prepareStatement(sql)) {

            // parámetros (pares NULL / valor)
            int k = 1;
            ps.setObject(k++, periodoId); ps.setObject(k++, periodoId);
            ps.setObject(k++, institucionId); ps.setObject(k++, institucionId);
            ps.setObject(k++, estadoId); ps.setObject(k++, estadoId);

        try (var rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new AlumnosPorNivel(
                    rs.getLong("nivel_id"),
                    rs.getString("nivel_nombre"),
                    rs.getLong("inscriptos"),
                    rs.getLong("cursando"),
                    rs.getLong("egresados"),
                    rs.getLong("abandono"),
                    rs.getLong("total")
                ));
            }
        }
    } catch (java.sql.SQLException ex) {
        throw new PersistenciaExcepciones("Error en reporte por nivel", ex);
    }
    return out;    }

    @Override
    public List<AlumnosPorCurso> cantidadAlumnosPorCurso(Long periodoId, Long institucionId, Integer estadoId) {
        List<AlumnosPorCurso> out = new ArrayList<>();
    final String sql = """
        SELECT
            cc.id AS curso_id,
            cc.nombre AS curso_nombre,
            SUM(CASE WHEN e.codigo = 'INSCRIPTO' THEN 1 ELSE 0 END) AS inscriptos,
            SUM(CASE WHEN e.codigo = 'CURSANDO'  THEN 1 ELSE 0 END) AS cursando,
            SUM(CASE WHEN e.codigo = 'EGRESADO'  THEN 1 ELSE 0 END) AS egresados,
            SUM(CASE WHEN e.codigo = 'ABANDONO'  THEN 1 ELSE 0 END) AS abandono,
            COUNT(*) AS total
        FROM matricula m
        JOIN estado_matricula e ON e.id = m.estado_id
        JOIN institucion_curso ic ON ic.id = m.institucion_curso_id
        JOIN curso_catalogo cc ON cc.id = ic.curso_catalogo_id
        WHERE (ic.institucion_id = ? OR ? IS NULL)
          AND (m.periodo_id = ? OR ? IS NULL)
        GROUP BY cc.id, cc.nombre
        ORDER BY cc.nombre
        """;

    try (Connection cn = DB.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setObject(1, institucionId);
        ps.setObject(2, institucionId);
        ps.setObject(3, periodoId);
        ps.setObject(4, periodoId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new AlumnosPorCurso(
                    rs.getLong("curso_id"),
                    rs.getString("curso_nombre"),
                    rs.getLong("inscriptos"),
                    rs.getLong("cursando"),
                    rs.getLong("egresados"),
                    rs.getLong("abandono"),
                    rs.getLong("total")
                ));
            }
        }
    } catch (SQLException e) {
        throw new PersistenciaExcepciones("Error al generar reporte por curso", e);
    }
    return out;
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

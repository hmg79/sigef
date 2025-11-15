/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Logica de comunicación hacia persistencia para la realización de los informes
*/
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.dto.*;
import edu.formosa.sigef.PERSISTENCIA.ReporteDao;
import edu.formosa.sigef.LOGICA.PermisosServicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ReporteServicio {
private final ReporteDao dao;
    public ReporteServicio(ReporteDao dao){ this.dao = dao; }

    public List<AlumnosPorLocalidad> cantidadAlumnosPorLocalidad(Long periodoId, Long institucionId, Long localidadId, Integer estadoId, UserSession sesion){
          if (sesion.esInstitucion()) {
            // Si el usuario es institución, fuerza su propio ID
             if (institucionId == null) {
                    institucionId = sesion.getInstitucionId();
            } else if (!Objects.equals(sesion.getInstitucionId(), institucionId)) {
                registrarAccesoDenegado(sesion, institucionId);
            throw new RuntimeException("Acceso denegado: no tiene permisos para esta institución.");
            } 
          }
          return dao.cantidadAlumnosPorLocalidad(periodoId, institucionId, localidadId, estadoId);
    }
    public List<AlumnosPorNivel> cantidadAlumnosPorNivel(Long periodoId, Long institucionId, Integer estadoId, UserSession sesion){
         if (sesion.esInstitucion()) {
            if (institucionId == null) {
                institucionId = sesion.getInstitucionId();
            } else if (!Objects.equals(sesion.getInstitucionId(), institucionId)) {
            registrarAccesoDenegado(sesion, institucionId);
            throw new RuntimeException("Acceso denegado: no tiene permisos para esta institución.");
           }
    }

    return dao.cantidadAlumnosPorNivel(periodoId, institucionId, estadoId);
    }
    public List<AlumnosPorCurso> cantidadAlumnosPorCurso(Long periodoId, Long institucionId, Integer estadoId, UserSession sesion){
        // --- Control de acceso seguro ---
    if (sesion.esInstitucion()) {
        if (institucionId == null) {
            institucionId = sesion.getInstitucionId(); // usa la propia institución
        } else if (!Objects.equals(sesion.getInstitucionId(), institucionId)) {
            registrarAccesoDenegado(sesion, institucionId);
            throw new RuntimeException("Acceso denegado: no tiene permisos para esta institución.");
        }
    }

    return dao.cantidadAlumnosPorCurso(periodoId, institucionId, estadoId);
    }
    public List<AlumnosPorCondicion> cantidadPorCondicion(Long periodoId, Long institucionId, UserSession sesion){
         if (sesion.esInstitucion()) {
        if (institucionId == null) {
            institucionId = sesion.getInstitucionId();
        } else if (!Objects.equals(sesion.getInstitucionId(), institucionId)) {
            registrarAccesoDenegado(sesion, institucionId);
            throw new RuntimeException("Acceso denegado: no tiene permisos para esta institución.");
        }
    }

    return dao.cantidadPorCondicion(periodoId, institucionId);  
    }
     /** Ordena una lista con un comparador
     * @param <T>
     * @param lista
     * @param cmp
     * @return  */
 
    public static <T> List<T> ordenar(List<T> lista, Comparator<T> cmp){
        ArrayList<T> out = new ArrayList<>(lista);
        Collections.sort(out, cmp);
        return out;
    }

    /** Búsqueda binaria por cursoId; PRE: lista ordenada por cursoId asc.
     * @param lista
     * @param cursoId
     * @return  */
    public static int busquedaBinariaPorCursoId(List<edu.formosa.sigef.LOGICA.dto.AlumnosPorCurso> lista, long cursoId){
        int lo=0, hi=lista.size()-1;
        while (lo<=hi){
            int mid = (lo+hi) >>> 1;
            long id = lista.get(mid).getCursoId();
            if (id == cursoId) return mid;
            if (id < cursoId) lo = mid + 1; else hi = mid - 1;
        }
        return -1;
    }
   // --- Auditoría de intentos denegados ---
private void registrarAccesoDenegado(UserSession sesion, Long institucionId) {
    Connection cn = null;
    PreparedStatement ps = null;

    try {
        cn = edu.formosa.sigef.PERSISTENCIA.DB.getConnection();
        String sql = """
            INSERT INTO sesion_acceso(usuario_id, ip, exito, mensaje, creado_en)
            VALUES (?, ?, ?, ?, NOW())
        """;
        ps = cn.prepareStatement(sql);

        ps.setObject(1, sesion != null ? sesion.getId() : null);
        ps.setString(2, obtenerIPLocal()); // usa IP real o loopback
        ps.setBoolean(3, false);
        ps.setString(4, "Acceso denegado a institución " + institucionId);
        ps.executeUpdate();

    } catch (Exception e) {
        System.err.println("⚠️ No se pudo registrar acceso denegado: " + e.getMessage());
    } finally {
        try {
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (Exception ignored) {}
    }
}

private String obtenerIPLocal() {
    try {
        return java.net.InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
        return "127.0.0.1"; // fallback seguro
    }
}
}
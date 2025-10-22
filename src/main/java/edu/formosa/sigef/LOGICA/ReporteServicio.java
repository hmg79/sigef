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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReporteServicio {
private final ReporteDao dao;
    public ReporteServicio(ReporteDao dao){ this.dao = dao; }

    public List<AlumnosPorLocalidad> cantidadAlumnosPorLocalidad(Long periodoId, Long institucionId, Long localidadId, Integer estadoId){
    return dao.cantidadAlumnosPorLocalidad(periodoId, institucionId, localidadId, estadoId);
    }
    public List<AlumnosPorNivel> cantidadAlumnosPorNivel(Long periodoId, Long institucionId, Integer estadoId){
        return dao.cantidadAlumnosPorNivel(periodoId, institucionId, estadoId);
    }
    public List<AlumnosPorCurso> cantidadAlumnosPorCurso(Long periodoId, Long institucionId, Integer estadoId){
        return dao.cantidadAlumnosPorCurso(periodoId, institucionId, estadoId);
    }
    public List<AlumnosPorCondicion> cantidadPorCondicion(Long periodoId, Long institucionId){
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
}

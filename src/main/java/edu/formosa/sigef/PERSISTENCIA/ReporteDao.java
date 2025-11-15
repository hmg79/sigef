/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/*
Logica de comunicación con la base de datos para realizar consultas de reportes
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author Héctor Marciano Galli <hectormgalli@gmail.com>
 */
import edu.formosa.sigef.LOGICA.dto.AlumnosPorLocalidad;
import edu.formosa.sigef.LOGICA.dto.AlumnosPorNivel;
import edu.formosa.sigef.LOGICA.dto.AlumnosPorCurso;
import edu.formosa.sigef.LOGICA.dto.AlumnosPorCondicion;
import java.util.List;
public interface ReporteDao {
    // Filtros : periodoId, institucionId, estadoId
    List<AlumnosPorLocalidad> cantidadAlumnosPorLocalidad(Long periodoId, Long institucionId, Long localidadId,Integer estadoId);
    List<AlumnosPorNivel>     cantidadAlumnosPorNivel(Long periodoId, Long institucionId, Integer estadoId);
    List<AlumnosPorCurso>     cantidadAlumnosPorCurso(Long periodoId, Long institucionId, Integer estadoId);
    List<AlumnosPorCondicion> cantidadPorCondicion(Long periodoId, Long institucionId);
}

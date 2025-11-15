/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/*
Interface de catalogo para listar por periodo o instituciones
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author Héctor Marciano Galli <hectormgalli@gmail.com>
 */
import edu.formosa.sigef.LOGICA.dto.InstitucionDTO;
import edu.formosa.sigef.LOGICA.dto.PeriodoDTO;
import edu.formosa.sigef.LOGICA.dto.LocalidadDTO;
import java.util.List;
public interface CatalogoDAO {
    List<PeriodoDTO> listarPeriodos();                   // SELECT id, nombre FROM periodo ORDER BY nombre
    List<InstitucionDTO> listarInstituciones();         // SELECT id, nombre FROM institucion ORDER BY nombre
    List<LocalidadDTO> listarLocalidades();
}

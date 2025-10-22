/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Logica para obtener el catalogo de institucion y periodo
*/
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */

import edu.formosa.sigef.LOGICA.dto.InstitucionDTO;
import edu.formosa.sigef.LOGICA.dto.PeriodoDTO;
import edu.formosa.sigef.LOGICA.dto.LocalidadDTO;
import edu.formosa.sigef.PERSISTENCIA.CatalogoDAO;

import java.util.List;
public class CatalogoServicio {
private final CatalogoDAO dao;
    public CatalogoServicio(CatalogoDAO dao){ this.dao = dao; }

    public List<PeriodoDTO> periodos(){ return dao.listarPeriodos(); }
    public List<InstitucionDTO> instituciones(){ return dao.listarInstituciones(); }
    public List<LocalidadDTO> localidades(){ return dao.listarLocalidades(); }

    // Estados fijos (si no hay tabla "estado")
    public enum EstadoOpt {
        TODOS(null, "Todos"),
        PRE(1, "Preinscripto"),
        CUR(2, "Cursando"),
        ABA(3, "Abandono"),
        EGR(4, "Egresado");
        private final Integer id; private final String label;
        EstadoOpt(Integer id, String label){ this.id=id; this.label=label; }
        public Integer id(){ return id; }
        @Override public String toString(){ return label; }
    }
}

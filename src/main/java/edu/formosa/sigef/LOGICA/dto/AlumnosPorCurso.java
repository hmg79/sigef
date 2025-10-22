/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Cantidad de alumnos por curso
*/
package edu.formosa.sigef.LOGICA.dto;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class AlumnosPorCurso {
    private final long cursoId;
    private final String cursoNombre;
    private final long total;
    public AlumnosPorCurso(long cursoId, String cursoNombre, long total){
        this.cursoId = cursoId; this.cursoNombre = cursoNombre; this.total = total;
    }
    public long getCursoId(){ return cursoId; }
    public String getCursoNombre(){ return cursoNombre; }
    public long getTotal(){ return total; }
}

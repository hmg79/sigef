/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Cantidad  de Alumnos por Nivel
*/
package edu.formosa.sigef.LOGICA.dto;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class AlumnosPorNivel {
private final long nivelId;
    private final String nivelNombre;
    private final long total;
    public AlumnosPorNivel(long nivelId, String nivelNombre, long total){
        this.nivelId = nivelId; this.nivelNombre = nivelNombre; this.total = total;
    }
    public long getNivelId(){ return nivelId; }
    public String getNivelNombre(){ return nivelNombre; }
    public long getTotal(){ return total; }
}

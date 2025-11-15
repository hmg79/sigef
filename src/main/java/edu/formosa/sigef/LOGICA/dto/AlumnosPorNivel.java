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
    private final long inscriptos;
    private final long cursando;
    private final long egreso;
    private final long abandono;
    private final long total;
    public AlumnosPorNivel(long nivelId, String nivelNombre,long inscriptos, long cursando, long egreso, long abandono, long total){
        this.nivelId = nivelId;
        this.nivelNombre = nivelNombre;
        this.inscriptos=inscriptos;
        this.cursando=cursando;
        this.egreso=egreso;
        this.abandono=abandono;
        this.total = total;
    }
    public long getNivelId(){ return nivelId; }
    public String getNivelNombre(){ return nivelNombre; }
    public long getInscriptos() {return inscriptos; }
    public long getCursando() {return cursando; }
    public long getEgreso() {return egreso; }
    public long getAbandono() {return abandono; }
    public long getTotal(){ return total; }
}

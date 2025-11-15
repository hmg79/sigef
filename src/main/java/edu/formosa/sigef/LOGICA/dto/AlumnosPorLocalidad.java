/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Cantidad de alumnos por localidad
*/
package edu.formosa.sigef.LOGICA.dto;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class AlumnosPorLocalidad {
    private final long localidadId;
    private final String localidadNombre;
    private final int total;
    private final int inscriptos;
    private final int cursando;
    private final int egresados;
    private final int abandono;
    public AlumnosPorLocalidad(long localidadId, String localidadNombre, int inscriptos, int cursando, int egresados,int abandono, int total){
        this.localidadId = localidadId;
        this.localidadNombre = localidadNombre;
        this.total = total;
        this.inscriptos = inscriptos;
        this.cursando = cursando;
        this.egresados = egresados;
        this.abandono=abandono;
        
    }
    public long getLocalidadId(){ return localidadId; }
    public String getLocalidadNombre(){ return localidadNombre; }
    public int getInscriptos() {return inscriptos;}
    public int getCursando() {return cursando;}
    public int getEgresados() {return egresados;}
    public int getAbandono(){return abandono;}
    public long getTotal(){ return total; }
}

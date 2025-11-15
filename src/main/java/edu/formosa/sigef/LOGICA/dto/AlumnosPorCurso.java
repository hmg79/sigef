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
    private final long inscriptos;
    private final long cursando;
    private final long egresados;
    private final long abandono;

    private final long total;
    public AlumnosPorCurso(long cursoId, String cursoNombre,long inscriptos, long cursando, long egresados, long abandono, long total){
        this.cursoId = cursoId;
        this.cursoNombre = cursoNombre;
        this.inscriptos=inscriptos;
        this.cursando=cursando;
        this.egresados=egresados;
        this.abandono=abandono;
        this.total = total;
    }
    public long getCursoId(){ return cursoId; }
    public String getCursoNombre(){ return cursoNombre; }
    public long getInscriptos(){return inscriptos;}
    public long getCursando(){return cursando;}
    public long getEgresados(){return egresados;}
    public long getAbandono() {return abandono;}
    public long getTotal(){ return total; }
}

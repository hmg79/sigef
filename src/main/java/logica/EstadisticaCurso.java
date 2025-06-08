/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package logica;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class EstadisticaCurso {
    private String tipo;
    private int inscripto;
    private int cursando;
    private int egresado;
    private int total;
    private int Id_Curso;
    private String NombreCurso;
    private String TipoCapacitacion;  

    public int getId_Curso() {
        return Id_Curso;
    }

    public void setId_Curso(int Id_Curso) {
        this.Id_Curso = Id_Curso;
    }

    public String getNombreCurso() {
        return NombreCurso;
    }

    public void setNombreCurso(String NombreCurso) {
        this.NombreCurso = NombreCurso;
    }

    public String getTipoCapacitacion() {
        return TipoCapacitacion;
    }

    public void setTipoCapacitacion(String TipoCapacitacion) {
        this.TipoCapacitacion = TipoCapacitacion;
    }

 
 
         public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getInscripto() {
        return inscripto;
    }

    public void setInscripto(int inscripto) {
        this.inscripto = inscripto;
    }

    public int getCursando() {
        return cursando;
    }

    public void setCursando(int cursando) {
        this.cursando = cursando;
    }

    public int getEgresado() {
        return egresado;
    }

    public void setEgresado(int egresado) {
        this.egresado = egresado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

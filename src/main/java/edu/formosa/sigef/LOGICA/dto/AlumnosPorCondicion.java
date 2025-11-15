/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Cantidad de alumnos por condición
*/
package edu.formosa.sigef.LOGICA.dto;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class AlumnosPorCondicion {
    private final int estadoId;
    private final String estado;
    private final long total;
    public AlumnosPorCondicion(int estadoId, String estado, long total){
        this.estadoId = estadoId; this.estado = estado; this.total = total;
    }
    public int getEstadoId(){ return estadoId; }
    public String getEstado(){ return estado; }
    public long getTotal(){ return total; }
}

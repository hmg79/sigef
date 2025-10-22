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
    private final long total;

    public AlumnosPorLocalidad(long localidadId, String localidadNombre, long total){
        this.localidadId = localidadId;
        this.localidadNombre = localidadNombre;
        this.total = total;
    }
    public long getLocalidadId(){ return localidadId; }
    public String getLocalidadNombre(){ return localidadNombre; }
    public long getTotal(){ return total; }
}

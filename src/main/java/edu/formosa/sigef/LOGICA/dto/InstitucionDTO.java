/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Logica para obtener las instituciones
*/
package edu.formosa.sigef.LOGICA.dto;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class InstitucionDTO {
    private final long id;
    private final String nombre;
    public InstitucionDTO(long id, String nombre){ this.id=id; this.nombre=nombre; }
    public long getId(){ return id; }
    public String getNombre(){ return nombre; }
    @Override public String toString(){ return nombre; }
}

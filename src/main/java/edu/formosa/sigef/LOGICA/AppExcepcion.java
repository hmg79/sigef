/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Creación de Excepciones Personalizadas
*/
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class AppExcepcion extends RuntimeException{
    public AppExcepcion(String mensaje) {super(mensaje);}
    public AppExcepcion(String mensaje, Throwable causa){ super(mensaje, causa);}
}

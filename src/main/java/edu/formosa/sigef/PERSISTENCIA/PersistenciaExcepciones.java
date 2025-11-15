/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Persistencia para las excepciones personalizadas
*/
package edu.formosa.sigef.PERSISTENCIA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.LOGICA.AppExcepcion;
public class PersistenciaExcepciones extends AppExcepcion {
    public PersistenciaExcepciones(String mensaje, Throwable causa){ super(mensaje, causa);}
}

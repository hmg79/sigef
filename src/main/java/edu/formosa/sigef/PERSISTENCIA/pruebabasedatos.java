/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
    Archivo de Prueba de conexión  a la base de datos
*/
package edu.formosa.sigef.PERSISTENCIA;

import java.sql.Connection;
/**
 *
 * @author HÉCTOR M GALLI
 */
public class pruebabasedatos {
    public static void main(String[] args) throws Exception {
        try (Connection cn = DB.getConnection()) {
            System.out.println("OK: Conectado a " + cn.getMetaData().getURL());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Se encargara de validar y verificar los permisos correspondientes
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class PermisosServicio {
/**
     * Verifica si el usuario puede acceder a una institución determinada.
     * @param sesion sesión actual del usuario logueado
     * @param institucionId id de la institución que se intenta consultar
     * @return true si tiene permiso, false si no
     */
    public static boolean puedeAcceder(UserSession sesion, Long institucionId) {
        if (sesion == null) return false;

        // Rol DEP: acceso total
        if ("DEP".equalsIgnoreCase(sesion.getRolCodigo())) {
            return true;
        }

        // Rol INST: solo su institución
        if ("INST".equalsIgnoreCase(sesion.getRolCodigo())) {
            return sesion.getInstitucionId() != null
                    && sesion.getInstitucionId().equals(institucionId);
        }

        // Otros roles, sin acceso
        return false;
    }
}

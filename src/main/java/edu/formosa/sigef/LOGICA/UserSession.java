/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
    Logica de la sessión de usuarios
*/
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */
public class UserSession {
 private final long userId;
    private final String username;
    private final String rolCodigo; // "DEP" o "INST"
    private final Long institucionId; // null para DEP

    public UserSession(long userId, String username, String rolCodigo, Long institucionId) {
        this.userId = userId; this.username = username; this.rolCodigo = rolCodigo; this.institucionId = institucionId;
    }
    public long getUserId(){ return userId; }
    public String getUsername(){ return username; }
    public String getRolCodigo(){ return rolCodigo; }
    public Long getInstitucionId(){ return institucionId; }
    public boolean esDepartamento(){ return "DEP".equalsIgnoreCase(rolCodigo); }
    public boolean esInstitucion(){ return "INSTITUCION".equalsIgnoreCase(rolCodigo); }

    Object getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
Servicio de autenticación de ususarios
*/
package edu.formosa.sigef.LOGICA;

/**
 *
 * @author HÉCTOR M GALLI
 */
import edu.formosa.sigef.PERSISTENCIA.AutenticacionDAO;
import java.util.Optional;
public class AuthServicio {
    private final AutenticacionDAO dao;
    public AuthServicio(AutenticacionDAO dao){ this.dao = dao; }
    public Optional<UserSession> login(String user, String pass){
        return dao.login(user, pass);
    }
}
